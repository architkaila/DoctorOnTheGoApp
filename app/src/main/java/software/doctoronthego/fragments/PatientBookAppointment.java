package software.doctoronthego.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import software.doctoronthego.PatientDetailsActivity;
import software.doctoronthego.R;

import static software.doctoronthego.fragments.PatientSignIn.mAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientBookAppointment extends Fragment {

    String userEmail;
    EditText fname, lname, email, datepicker, timepicker;
    FirebaseUser currentUser;
    Button submit;
    DocumentReference db;

    public PatientBookAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient_book_appointment, container, false);

        fname = v.findViewById(R.id.fname);
        lname = v.findViewById(R.id.lname);
        email = v.findViewById(R.id.email);
        currentUser = mAuth.getCurrentUser();
        userEmail = currentUser.getEmail();
        submit = v.findViewById(R.id.submit);
        datepicker = v.findViewById(R.id.datepicker);
        timepicker = v.findViewById(R.id.timepicker);

        db = FirebaseFirestore.getInstance().collection("patientData").document(userEmail);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        db.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    String mFirstName = (String) documentSnapshot.get("first_name");
                    String mLastName = (String) documentSnapshot.get("last_name");
                    fname.setText(mFirstName);
                    lname.setText(mLastName);
                    email.setText(userEmail);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> AppointmentData = new HashMap<>();
                AppointmentData.put("date", datepicker.getText().toString());
                AppointmentData.put("time", timepicker.getText().toString());

                db.collection("Appointments").document().set(AppointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("PatientBookAppointment", "Saved");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("PatientBookAppointment", "Not Saved");
                    }
                });

                startActivity(new Intent(getActivity(), PatientDetailsActivity.class));
            }
        });

        timepicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                final int hh = c.get(Calendar.HOUR_OF_DAY);
                final int mi = c.get(Calendar.MINUTE);

                TimePickerDialog tp = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;

                        if (hourOfDay < hh) {
                            Toast.makeText(getActivity(), "Enter Correct Time!", Toast.LENGTH_SHORT).show();
                            timepicker.setText("");
                        } else if (minute < mi) {
                            Toast.makeText(getActivity(), "Enter Correct Time!", Toast.LENGTH_SHORT).show();
                            timepicker.setText("");
                        } else {
                            timepicker.setText(time);
                        }

                    }
                }, hh, mi, false);
                tp.show();
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                final int dd = c.get(Calendar.DAY_OF_MONTH);
                final int mm = c.get(Calendar.MONTH);
                final int yy = c.get(Calendar.YEAR);

                DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        String dateStr;
                        String monthStr;

                        if (month + 1 < 10) {
                            monthStr = "0" + (month + 1);
                        } else {
                            monthStr = "" + (month + 1);
                        }

                        if (dayOfMonth < 10) {
                            dateStr = "0" + dayOfMonth;
                        } else {
                            dateStr = "" + dayOfMonth;
                        }


                        String date = year + "-" + monthStr + "-" + dateStr;
//                        if (year < yy) {
//                            Toast.makeText(getActivity(), "Enter Correct Date!", Toast.LENGTH_SHORT).show();
//                            datepicker.setText("");
//                        } else if ((month + 1) < mm) {
//                            Toast.makeText(getActivity(), "Enter Correct Date!", Toast.LENGTH_SHORT).show();
//                            datepicker.setText("");
//                        } else if (dayOfMonth < dd) {
//                            Toast.makeText(getActivity(), "Enter Correct Date!", Toast.LENGTH_SHORT).show();
//                            datepicker.setText("");
//                        } else {
//                            datepicker.setText(date);
//                        }
                        datepicker.setText(date);

                    }
                }, yy, mm, dd);
                dp.show();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
}