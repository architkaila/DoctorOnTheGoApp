package software.doctoronthego;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;
import static software.doctoronthego.fragments.PatientSignIn.mAuth;

public class ViewPrescription extends AppCompatActivity {

    TextView date;
    TextView editPrescription;
    String userEmail;
    String documentName;
    String appointmentDate;
    String appointmentTime;
    FirebaseUser currentUser;
    DocumentReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);

        date = findViewById(R.id.date);
        editPrescription = findViewById(R.id.edit_prescription);
        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ViewPrescription.this, PatientDetailsActivity.class));
    }
}
