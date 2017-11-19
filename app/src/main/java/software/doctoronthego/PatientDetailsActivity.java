package software.doctoronthego;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import software.doctoronthego.fragments.PatientAdaptor;
import software.doctoronthego.fragments.PatientSignIn;

public class PatientDetailsActivity extends AppCompatActivity {

    PatientSignIn p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        ViewPager viewPager = findViewById(R.id.viewpager);
        PatientAdaptor adapter = new PatientAdaptor(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.patientmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            //add part to logout from database too
            signOut();
            startActivity(new Intent(PatientDetailsActivity.this, PatientActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        PatientSignIn.mAuth.signOut();
    }
}
