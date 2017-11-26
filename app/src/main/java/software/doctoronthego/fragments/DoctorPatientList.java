package software.doctoronthego.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import software.doctoronthego.PatientList;
import software.doctoronthego.PatientListAdaptor;
import software.doctoronthego.R;

import static android.content.ContentValues.TAG;
import static software.doctoronthego.fragments.PatientSignIn.mAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorPatientList extends Fragment {

    String userEmail;
    FirebaseUser currentUser;
    CollectionReference db;
    ListView list;

    StorageReference mStorage;

    public DoctorPatientList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor_patient_list, container, false);

        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();

        mStorage = FirebaseStorage.getInstance().getReference();

        list = v.findViewById(R.id.list);
        db = FirebaseFirestore.getInstance().collection("patientData");
        final String[] photoUri = {""};
        db.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                final ArrayList<PatientList> patients = new ArrayList<PatientList>();
                final PatientListAdaptor adptr = new PatientListAdaptor(getActivity(), patients);

                if (task.isSuccessful()) {
                    for (final DocumentSnapshot doc : task.getResult()) {

                        mStorage.child("Photos").child(doc.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                photoUri[0] = uri.toString();
                                Log.e("ChittuChutiya", photoUri[0]);
                                //Toast.makeText(getActivity(),data.getmPhotoUri().toString(), Toast.LENGTH_SHORT).show();
                            }

                        });
                        Log.e("ChittuChutiya1", photoUri[0]);
                        PatientList data = new PatientList(doc.getString("first_name") + " " + doc.getString("last_name"), photoUri[0]);
                        patients.add(data);

                        adptr.setNotifyOnChange(true);

                    }

                    list.setAdapter(adptr);

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


        return v;
    }

}
