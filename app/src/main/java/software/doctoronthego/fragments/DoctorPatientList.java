package software.doctoronthego.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

import software.doctoronthego.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorPatientList extends Fragment {

    DocumentReference db;
    TextView patient_list;
    StringBuilder data = new StringBuilder();

    public DoctorPatientList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor_patient_list, container, false);
        return v;
    }

}
