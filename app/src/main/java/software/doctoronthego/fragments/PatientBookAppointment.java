package software.doctoronthego.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import software.doctoronthego.R;

import static software.doctoronthego.fragments.PatientSignIn.mAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientBookAppointment extends Fragment {

    String userEmail;
    EditText fname, lname, email, date, time;
    FirebaseUser currentUser;
    Button submit;
    DocumentReference db;

    public PatientBookAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient_book_appointment, container, false);

        fname = v.findViewById(R.id.fname);
        lname = v.findViewById(R.id.lname);
        email = v.findViewById(R.id.email);
        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();
        submit = v.findViewById(R.id.submit);
        date = v.findViewById(R.id.datepicker);
        time = v.findViewById(R.id.timepicker);

        db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        db.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    String mFirstName = (String) documentSnapshot.get("first_name");
                    String mLastName = (String) documentSnapshot.get("last_name");
                    fname.setText(mFirstName);
                    lname.setText(mLastName);
                    email.setText(userEmail);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> AppointmentData = new HashMap<>();
                AppointmentData.put("date", date.getText().toString());
                AppointmentData.put("time", time.getText().toString());

                db.collection("Appointments").document().set(AppointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("PatientBookAppointment", "Saved");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("PatientBookAppointment", "Not Saved");
                    }
                });
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
}