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
import android.widget.TextView;
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
public class PatientSignIn extends Fragment {

    private static final String TAG = "EmailPassword";
    Button signin;
    Button logout;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    public PatientSignIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient_sign_in, container, false);
        signin = v.findViewById(R.id.patient_sign_in);
        logout = v.findViewById(R.id.logout);

        mStatusTextView = v.findViewById(R.id.status);
        mDetailTextView = v.findViewById(R.id.result);
        mEmailField = v.findViewById(R.id.patient_signin_email);
        mPasswordField = v.findViewById(R.id.patient_signin_password);
        mAuth = FirebaseAuth.getInstance();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            mStatusTextView.setText("Signed out");
            mDetailTextView.setText(null);
        } else {
            mStatusTextView.setText(getString(R.string.b, currentUser.getEmail(), currentUser.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.a, currentUser.getUid()));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mStatusTextView.setText(getString(R.string.b, user.getEmail(), user.isEmailVerified()));
                            mDetailTextView.setText(getString(R.string.a, user.getUid()));

                            //startActivity(new Intent(getActivity(), PatientDetailsActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            mStatusTextView.setText("Signed out");
                            mDetailTextView.setText(null);

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText("Authentication Failed");
                        }
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        mStatusTextView.setText("Signed out");
        mDetailTextView.setText(null);
        //updateUI(null);
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
