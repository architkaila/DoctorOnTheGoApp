package software.doctoronthego.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import software.doctoronthego.PatientProfileUpdate;
import software.doctoronthego.R;

import static software.doctoronthego.fragments.PatientSignIn.mAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientProfile extends Fragment {


    public static final int MAX_WIDTH = 500;
    public static final int MAX_HEIGHT = 500;
    String userEmail;
    FirebaseUser currentUser;
    Button updateProfile;
    DocumentReference db;
    TextView email, fname, lname, age, address;
    StorageReference mStorage;
    ImageView patientProfile;
    ProgressDialog mProgressDialogue;

    public PatientProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient_profile, container, false);

        updateProfile = v.findViewById(R.id.updateProfile);
        fname = v.findViewById(R.id.fname1);
        lname = v.findViewById(R.id.lname1);
        age = v.findViewById(R.id.age1);
        address = v.findViewById(R.id.address1);
        email = v.findViewById(R.id.email1);
        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();
        patientProfile = v.findViewById(R.id.patientPhoto);

        mStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialogue = new ProgressDialog(getActivity());

        db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        mProgressDialogue.setMessage("Loading...");
        mProgressDialogue.show();

        db.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    String mFirstName = (String) documentSnapshot.get("first_name");
                    String mLastName = (String) documentSnapshot.get("last_name");
                    String mAge = (String) documentSnapshot.get("age");
                    String mAddress = (String) documentSnapshot.get("address");
                    fname.setText(mFirstName);
                    lname.setText(mLastName);
                    age.setText(mAge);
                    email.setText(userEmail);
                    address.setText(mAddress);
                }
            }
        });

        mStorage.child("Photos").child(userEmail).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //patientProfile.setImageURI(uri);

                Picasso.with(getActivity()).load(uri).fit().centerCrop()
                        .into(patientProfile);

//                Picasso.with(getActivity())
//                        .load(uri)
//                        .resize(MAX_WIDTH,MAX_HEIGHT)
//                        .onlyScaleDown()
//                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                        .into(patientProfile);
                mProgressDialogue.dismiss();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PatientProfileUpdate.class));
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
}
