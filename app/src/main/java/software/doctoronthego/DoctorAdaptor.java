package software.doctoronthego;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import software.doctoronthego.fragments.DoctorPatientList;
import software.doctoronthego.fragments.DoctorViewAppointment;

/**
 * Created by archit on 19/11/17.
 */

public class DoctorAdaptor extends FragmentPagerAdapter {

    private Context mContext;

    public DoctorAdaptor(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DoctorViewAppointment();
        } else {
            return new DoctorPatientList();
        }
    }


    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.patients_list);
        } else {
            return mContext.getString(R.string.patient_view_appointment);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
