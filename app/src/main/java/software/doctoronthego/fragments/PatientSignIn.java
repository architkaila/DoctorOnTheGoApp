package software.doctoronthego.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import software.doctoronthego.PatientDetailsActivity;
import software.doctoronthego.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientSignIn extends Fragment {

    Button signin;


    public PatientSignIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient_sign_in, container, false);
        signin = v.findViewById(R.id.patient_sign_in);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PatientDetailsActivity.class));
            }
        });

        super.onActivityCreated(savedInstanceState);
    }
}
