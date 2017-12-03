package software.doctoronthego;

/**
 * Created by archit on 22/11/17.
 */

public class Appointments {

    private String Date;
    private String Time;
    private String Prescription;
    private String DocumentId;

    public Appointments() {

    }

    public Appointments(String date, String time, String prescripton, String documentId) {

        Date = date;
        Time = time;
        Prescription = prescripton;
        DocumentId = documentId;
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

    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(String prescription) {
        Prescription = prescription;
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }
}
