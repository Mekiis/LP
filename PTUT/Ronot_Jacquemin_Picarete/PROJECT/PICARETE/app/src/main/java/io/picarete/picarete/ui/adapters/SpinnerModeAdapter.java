package io.picarete.picarete.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import io.picarete.picarete.R;
import io.picarete.picarete.ui.custom.CustomFontTextView;

/**
 * Created by iem on 07/01/15.
 */
public class SpinnerModeAdapter extends ArrayAdapter<String>{

    private String mNames[];
    private String mDescriptions[];
    private LayoutInflater inflater;

    public SpinnerModeAdapter(Context context, int resource, String names[], String descriptions[]) {
        super(context, resource, names);
        this.mNames = names;
        this.mDescriptions = descriptions;
        inflater = ((Activity)context).getLayoutInflater();
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder; // to reference the child views for later actions
        if (view == null) {
            view = inflater.inflate(R.layout.item_spinner_mode, parent, false);
            holder = new ViewHolder();
            holder.title = (CustomFontTextView)view.findViewById(R.id.mode_name);
            holder.description = (CustomFontTextView)view.findViewById(R.id.mode_desc);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.title.setText(mNames[position]);
        holder.description.setText(mDescriptions[position]);

        return view;
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder; // to reference the child views for later actions
        if (view == null) {
            view = inflater.inflate(R.layout.item_spinner_mode, parent, false);
            holder = new ViewHolder();
            holder.title = (CustomFontTextView)view.findViewById(R.id.mode_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.title.setText(mNames[position]);

        return view;
    }

    private class ViewHolder {
        CustomFontTextView title;
        CustomFontTextView description;
    }



}
