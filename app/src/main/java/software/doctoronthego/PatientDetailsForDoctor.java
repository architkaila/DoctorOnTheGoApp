package software.doctoronthego;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class PatientDetailsForDoctor extends AppCompatActivity {

    public static String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_for_doctor);

        ViewPager viewPager = findViewById(R.id.viewpager);
        PatientDetailsForDoctorAdaptor adapter = new PatientDetailsForDoctorAdaptor(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        s = getIntent().getStringExtra("email");
        //Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }

    public String getUserEmail() {
        return s;
    }
}
