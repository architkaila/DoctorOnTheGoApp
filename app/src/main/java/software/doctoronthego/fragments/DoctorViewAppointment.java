package software.doctoronthego.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import software.doctoronthego.AppointmentsDoctor;
import software.doctoronthego.AppointmentsDoctorAdaptor;
import software.doctoronthego.R;

import static android.content.ContentValues.TAG;
import static software.doctoronthego.fragments.PatientSignIn.mAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorViewAppointment extends Fragment {

    String userEmail;
    FirebaseUser currentUser;
    CollectionReference db;
    ListView list;


    public DoctorViewAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor_view_appointment, container, false);

        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();

        list = v.findViewById(R.id.list);
        db = FirebaseFirestore.getInstance().collection("patientData");

        db.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                final ArrayList<AppointmentsDoctor> appointments = new ArrayList<AppointmentsDoctor>();
                final AppointmentsDoctorAdaptor adptr = new AppointmentsDoctorAdaptor(getActivity(), appointments);

                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {

                        Toast.makeText(getActivity(), doc.getId(), Toast.LENGTH_SHORT).show();
                        String email = doc.getId();
                        final String name = doc.getString("first_name");

                        CollectionReference db1 = FirebaseFirestore.getInstance().collection("patientData").document(email).collection("Appointments");

                        db1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                if (task1.isSuccessful()) {
                                    for (DocumentSnapshot doc1 : task1.getResult()) {
                                        //AppointmentsDoctor data = doc1.toObject(AppointmentsDoctor.class);
                                        AppointmentsDoctor data = new AppointmentsDoctor(name, doc1.getString("date"), doc1.getString("time"));
                                        appointments.add(data);
                                        adptr.setNotifyOnChange(true);
                                    }
                                    list.setAdapter(adptr);
                                }
                            }
                        });
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        return v;
    }

}
