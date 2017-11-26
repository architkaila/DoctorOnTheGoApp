package software.doctoronthego;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by archit on 23/11/17.
 */

public class PatientListAdaptor extends ArrayAdapter<PatientList> {

    public PatientListAdaptor(Context context, ArrayList<PatientList> patients) {
        super(context, 0, patients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        PatientList data = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.patient_list, parent, false);
        }

        TextView name = listItemView.findViewById(R.id.name);
        ImageView patientImage = listItemView.findViewById(R.id.patientImage);

        name.setText(data.getName());
        //patientImage.setImageURI(Uri.parse(data.getmPhotoUri()));
        patientImage.setImageResource(R.drawable.unnamed);
        // Glide.with(getContext()).load(Uri.parse(data.getmPhotoUri())).into(patientImage);

        return listItemView;
    }
}
