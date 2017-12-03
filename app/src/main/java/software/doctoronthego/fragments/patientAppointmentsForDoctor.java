package software.doctoronthego.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import software.doctoronthego.Appointments;
import software.doctoronthego.AppointmentsAdaptor;
import software.doctoronthego.EditPrescription;
import software.doctoronthego.PatientDetailsForDoctor;
import software.doctoronthego.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class patientAppointmentsForDoctor extends Fragment {

    final ArrayList<Appointments> appointments = new ArrayList<>();
    String userEmail;
    FirebaseUser currentUser;
    DocumentReference db;
    ListView list;


    public patientAppointmentsForDoctor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient_appointments_for_doctor, container, false);

        PatientDetailsForDoctor obj = (PatientDetailsForDoctor) getActivity();
        userEmail = obj.getUserEmail();

        list = v.findViewById(R.id.list);
        db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail);

        db.collection("Appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        final AppointmentsAdaptor adptr = new AppointmentsAdaptor(getActivity(), appointments);

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                //  Appointments data = doc.toObject(Appointments.class);
                                Appointments data = new Appointments(doc.get("date").toString(), doc.get("time").toString(), doc.get("prescription").toString(), doc.getId());
                                appointments.add(data);
                                adptr.setNotifyOnChange(true);
                                Toast.makeText(getActivity(), data.getPrescription(), Toast.LENGTH_SHORT).show();
                            }
                            Collections.sort(appointments, new dateSort());
                            list.setAdapter(adptr);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent obj = new Intent(getActivity(), EditPrescription.class);
                obj.putExtra("document_name", appointments.get(i).getDocumentId());
                startActivity(obj);
                //startActivity(new Intent(getActivity(), PatientDetailsForDoctor.class));
            }
        });

        return v;
    }

    public class dateSort implements Comparator<Appointments> {
        public int compare(Appointments one, Appointments two) {
            return (two.getDate().compareTo(one.getDate()));
        }
    }

}
