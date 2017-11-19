package software.doctoronthego.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    TextView appointments;
    StringBuilder data = new StringBuilder();

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
        appointments = v.findViewById(R.id.appointments);
        db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db.collection("Appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            data.setLength(0);
                            for (DocumentSnapshot doc : task.getResult()) {
                                data.append(doc.getString("date")).append("    ").append(doc.getString("time")).append("\n");
                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                            }
                            appointments.setText(data.toString());

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}