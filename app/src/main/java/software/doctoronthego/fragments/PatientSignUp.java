package software.doctoronthego.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import software.doctoronthego.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientSignUp extends Fragment {

    private static final String TAG = "abcd";
    Button signup;
    private FirebaseAuth mAuth;
    // private TextView mStatusTextView;
    //  private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;

    public PatientSignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient_sign_up, container, false);

        //  mStatusTextView = v.findViewById(R.id.status);
        //  mDetailTextView = v.findViewById(R.id.result);
        mEmailField = v.findViewById(R.id.patient_reg_email);
        mPasswordField = v.findViewById(R.id.patient_reg_password);
        signup = v.findViewById(R.id.patient_reg_sign_up);
        mAuth = FirebaseAuth.getInstance();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            //    mStatusTextView.setText("Signed out");
            //    mDetailTextView.setText(null);
        } else {
            //    mStatusTextView.setText(getString(R.string.b, currentUser.getEmail(), currentUser.isEmailVerified()));
            //    mDetailTextView.setText(getString(R.string.a, currentUser.getUid()));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getActivity(), "Signup Successful !", Toast.LENGTH_LONG).show();
                            //     mStatusTextView.setText(getString(R.string.b, user.getEmail(), user.isEmailVerified()));
                            //     mDetailTextView.setText(getString(R.string.a, user.getUid()));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //    mStatusTextView.setText("Signed out");
                            //    mDetailTextView.setText(null);
                        }

                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }
}