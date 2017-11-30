package software.doctoronthego;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import software.doctoronthego.fragments.patientAppointmentsForDoctor;
import software.doctoronthego.fragments.patientProfileForDoctor;

/**
 * Created by archit on 27/11/17.
 */

public class PatientDetailsForDoctorAdaptor extends FragmentPagerAdapter {

    private Context mContext;

    public PatientDetailsForDoctorAdaptor(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new patientProfileForDoctor();
        } else {
            return new patientAppointmentsForDoctor();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.patient_profile);
        } else {
            return mContext.getString(R.string.patient_appointments);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
