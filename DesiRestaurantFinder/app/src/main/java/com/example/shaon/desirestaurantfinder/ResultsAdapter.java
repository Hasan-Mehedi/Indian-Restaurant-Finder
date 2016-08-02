package com.example.shaon.desirestaurantfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultsAdapter extends ArrayAdapter{

    ArrayList<Results> results;
    Context context;

    public ResultsAdapter(Context context, ArrayList<Results> results) {
        super(context, R.layout.single_row, results);
        this.results = results;
        this.context = context;
    }

    class ResultsViewHolder{
        TextView myName;
        TextView myAddress;
        TextView myZipcode;
        TextView myCity;

        ResultsViewHolder(View v){
            myName = (TextView) v.findViewById(R.id.nameField);
            myAddress = (TextView) v.findViewById(R.id.addressField);
            myZipcode = (TextView) v.findViewById(R.id.zipcodeField);
            myCity = (TextView) v.findViewById(R.id.cityField);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultsViewHolder holder = null;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row, parent, false);
            holder = new ResultsViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ResultsViewHolder) row.getTag();
        }
        Results result = results.get(position);
        holder.myName.setText(result.name);
        holder.myAddress.setText(result.address);
        holder.myZipcode.setText("Zipcode: "+result.zipcode);
        holder.myCity.setText("City: "+result.city);

        return row;
    }
}
