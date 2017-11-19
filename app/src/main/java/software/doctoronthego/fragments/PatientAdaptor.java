package software.doctoronthego.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import software.doctoronthego.R;

/**
 * Created by archit on 13/11/17.
 */

public class PatientAdaptor extends FragmentPagerAdapter {

    private Context mContext;

    public PatientAdaptor(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PatientProfile();
        }
        else if(position == 1) {
            return new PatientBookAppointment();
        } else if (position == 2) {
            return new PatientPrescriptions();
        } else {
            return new PatientViewAppointment();
        }
    }

    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.profile);
        }
        else if(position == 1){
            return mContext.getString(R.string.patient_book_appointment);
        } else if (position == 2) {
            return mContext.getString(R.string.patient_prescription);
        } else {
            return mContext.getString(R.string.patient_view_appointment);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
