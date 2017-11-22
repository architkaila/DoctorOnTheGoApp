package software.doctoronthego.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import software.doctoronthego.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorViewAppointment extends Fragment {


    public DoctorViewAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor_view_appointment, container, false);

        return v;
    }

}
