package software.doctoronthego;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PatientProfileUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_update);

        EditText fname = findViewById(R.id.fname1);
        EditText lname = findViewById(R.id.lname1);
        EditText age = findViewById(R.id.age1);
        EditText address = findViewById(R.id.address1);
        ImageView image = findViewById(R.id.image_view);
        Button update = findViewById(R.id.updateProfile);
    }
}
