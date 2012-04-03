package co.uk.alt236.restclient4android.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HeadersListAdapter extends ArrayAdapter<Pair<String, String>> {

    private ArrayList<Pair<String, String>> items;
    private Context context;

    public HeadersListAdapter(Context context, int textViewResourceId, ArrayList<Pair<String, String>> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_2, null);
        }

        Pair<String, String> item = items.get(position);
        
        if (item!= null) {
            // My layout has only one TextView
            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
            
            text1.setText(item.first);
            text2.setText(item.second);
         }

        return view;
    }
}
