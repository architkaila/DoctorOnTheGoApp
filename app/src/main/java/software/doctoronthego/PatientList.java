package software.doctoronthego;

/**
 * Created by archit on 23/11/17.
 */

public class PatientList {

    private String Name;
    private String mPhotoUri;

    public PatientList(String name, String photoUri) {
        Name = name;
        mPhotoUri = photoUri;
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
}
