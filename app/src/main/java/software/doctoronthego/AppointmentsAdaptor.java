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
 * Created by archit on 22/11/17.
 */

public class AppointmentsAdaptor extends ArrayAdapter<Appointments> {

    public AppointmentsAdaptor(Context context, ArrayList<Appointments> Appointment) {
        super(context, 0, Appointment);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Appointments data = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.appointment_list, parent, false);
        }

        TextView date = listItemView.findViewById(R.id.date);
        TextView time = listItemView.findViewById(R.id.time);

        date.setText(data.getDate());
        time.setText(data.getTime());
        return listItemView;
    }
}
