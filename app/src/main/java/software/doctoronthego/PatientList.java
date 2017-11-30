package software.doctoronthego;

/**
 * Created by archit on 23/11/17.
 */

public class PatientList {

    private String Name;
    private String mPhotoUri;
    private String mEmail;
    private String mPrescription;

    public PatientList(String name, String photoUri, String email) {
        Name = name;
        mPhotoUri = photoUri;
        mEmail = email;
    }

    public PatientList() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getmPhotoUri() {
        return mPhotoUri;
    }

    public void setmPhotoUri(String mPhotoUri) {
        this.mPhotoUri = mPhotoUri;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPrescription() {
        return mPrescription;
    }

    public void setmPrescription(String mPrescription) {
        this.mPrescription = mPrescription;
    }
}
