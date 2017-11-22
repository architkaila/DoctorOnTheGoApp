package software.doctoronthego;

/**
 * Created by archit on 23/11/17.
 */

public class PatientList {

    private String Name;

    public PatientList(String name) {
        Name = name;
    }

    public PatientList() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
