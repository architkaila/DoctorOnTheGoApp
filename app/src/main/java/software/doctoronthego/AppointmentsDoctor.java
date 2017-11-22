package software.doctoronthego;

/**
 * Created by archit on 23/11/17.
 */

public class AppointmentsDoctor {

    private String Name;
    private String Date;
    private String Time;

    public AppointmentsDoctor() {

    }

    public AppointmentsDoctor(String name, String date, String time) {
        Name = name;
        Date = date;
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
