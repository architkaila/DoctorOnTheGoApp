package software.doctoronthego;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by archit on 23/11/17.
 */

public class AppointmentsDoctorAdaptor extends ArrayAdapter<AppointmentsDoctor> {

    public AppointmentsDoctorAdaptor(Context context, ArrayList<AppointmentsDoctor> Appointment) {
        super(context, 0, Appointment);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AppointmentsDoctor data = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.appointment_doctor_list, parent, false);
        }

        TextView name = listItemView.findViewById(R.id.name);
        TextView datetime = listItemView.findViewById(R.id.datetime);

        name.setText(data.getName());
        datetime.setText(data.getDate() + " , " + data.getTime());
        return listItemView;
    }
}
