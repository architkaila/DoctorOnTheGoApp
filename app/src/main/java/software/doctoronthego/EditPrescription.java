package software.doctoronthego;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EditPrescription extends AppCompatActivity {

    TextView date;
    TextView editPrescription;
    Button save;
    String userEmail;
    String documentName;
    String appointmentDate;
    String appointmentTime;
    FirebaseUser currentUser;
    DocumentReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prescription);

        date = findViewById(R.id.date);
        editPrescription = findViewById(R.id.edit_prescription);
        save = findViewById(R.id.save);

        PatientDetailsForDoctor obj = new PatientDetailsForDoctor();
        userEmail = obj.getUserEmail();

        documentName = getIntent().getStringExtra("document_name");

        db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail).collection("Appointments").document(documentName);

        db.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        date.setText("Prescription: " + document.get("date").toString() + ", " + document.get("time").toString());
                        editPrescription.setText(document.get("prescription").toString());
                        appointmentDate = document.get("date").toString();
                        appointmentTime = document.get("time").toString();
//                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> prescriptionData = new HashMap<>();
                prescriptionData.put("date", appointmentDate);
                prescriptionData.put("time", appointmentTime);
                prescriptionData.put("prescription", editPrescription.getText().toString());

                db.set(prescriptionData).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                startActivity(new Intent(EditPrescription.this, DoctorActivity.class));
            }
        });

    }
}
