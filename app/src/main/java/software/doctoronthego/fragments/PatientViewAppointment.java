package software.doctoronthego.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import software.doctoronthego.Appointments;
import software.doctoronthego.AppointmentsAdaptor;
import software.doctoronthego.R;

import static android.content.ContentValues.TAG;
import static software.doctoronthego.fragments.PatientSignIn.mAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientViewAppointment extends Fragment {

    String userEmail;
    FirebaseUser currentUser;
    DocumentReference db;
    ListView list;

    public PatientViewAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient_view_appointment, container, false);

        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();

        list = v.findViewById(R.id.list);
        db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail);

        db.collection("Appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        final ArrayList<Appointments> appointments = new ArrayList<Appointments>();
                        final AppointmentsAdaptor adptr = new AppointmentsAdaptor(getActivity(), appointments);

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Appointments data = doc.toObject(Appointments.class);
                                appointments.add(data);
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

    @Override
    public void onStart() {
        super.onStart();

    }
}