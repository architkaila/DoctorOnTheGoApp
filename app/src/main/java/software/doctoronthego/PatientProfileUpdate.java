package software.doctoronthego;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import static software.doctoronthego.fragments.PatientSignIn.mAuth;

public class PatientProfileUpdate extends AppCompatActivity {

    public static final int GALLERY_INTENT = 2;
    String email;
    EditText fname, lname, age, address;
    ImageView image;
    Button addPhoto;
    CollectionReference db = FirebaseFirestore.getInstance().collection("patientData");
    FirebaseUser currentUser;
    ProgressDialog mProgressDialogue;
    StorageReference mStorage;
    String tempUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_update);

        mStorage = FirebaseStorage.getInstance().getReference();

        currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();
        fname = findViewById(R.id.fname1);
        lname = findViewById(R.id.lname1);
        age = findViewById(R.id.age1);
        address = findViewById(R.id.address1);

        Button update = findViewById(R.id.updateProfile);

        mProgressDialogue = new ProgressDialog(this);

        image = findViewById(R.id.image_view);
        addPhoto = findViewById(R.id.addPhoto);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

//        mStorage.child("Photos").child(email).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                tempUri = uri.toString();
//                mProgressDialogue.dismiss();
//            }
//        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> docData = new HashMap<>();
                docData.put("photoURI", tempUri);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            mProgressDialogue.setMessage("Uploading Image...");
            mProgressDialogue.show();

            final Uri uri = data.getData();

            StorageReference filePath = mStorage.child("Photos").child(email);
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    image.setImageURI(uri);
                    mProgressDialogue.dismiss();
                    mStorage.child("Photos").child(email).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            tempUri = uri.toString();
                            mProgressDialogue.dismiss();
                        }
                    });
                    Toast.makeText(PatientProfileUpdate.this, "Photo Upload Successful !", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PatientProfileUpdate.this, "Phot upload Failed !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
