package software.doctoronthego;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import software.doctoronthego.fragments.PatientSignIn;
import software.doctoronthego.fragments.PatientSignUp;

/**
 * Created by archit on 11/11/17.
 */

public class AuthenticateAdaptor extends FragmentPagerAdapter {

    private Context mContext;

    public AuthenticateAdaptor(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PatientSignIn();
        }
        else {
            return new PatientSignUp();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.signin);
        }
        else {
            return mContext.getString(R.string.signup);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
