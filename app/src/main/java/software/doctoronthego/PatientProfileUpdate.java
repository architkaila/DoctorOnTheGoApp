package software.doctoronthego;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static software.doctoronthego.fragments.PatientSignIn.mAuth;

public class PatientProfileUpdate extends AppCompatActivity {

    String email;
    EditText fname, lname, age, address;
    ImageView image;
    CollectionReference db = FirebaseFirestore.getInstance().collection("patientData");
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_update);

        currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();
        fname = findViewById(R.id.fname1);
        lname = findViewById(R.id.lname1);
        age = findViewById(R.id.age1);
        address = findViewById(R.id.address1);
        //ImageView image = findViewById(R.id.image_view);
        Button update = findViewById(R.id.updateProfile);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> docData = new HashMap<>();
                docData.put("email", email);
                docData.put("first_name", fname.getText().toString());
                docData.put("last_name", lname.getText().toString());
                docData.put("age", age.getText().toString());
                docData.put("address", address.getText().toString());

                db.document(email).set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("Patient ", "Data saved");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Error ", "Data Not Saved !");
                    }
                });

                startActivity(new Intent(PatientProfileUpdate.this, PatientDetailsActivity.class));
            }

        });
    }
}
