package mobi.braincode.pushegro.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import mobi.braincode.pushegro.client.model.QueryItem;

import java.util.ArrayList;

public class QueryListAdapter extends ArrayAdapter<QueryItem> {

    public QueryListAdapter(Context context, ArrayList<QueryItem> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        QueryItem query = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.query_list_item, parent, false);
        }
        // Lookup view for data population
        TextView itemText = (TextView) convertView.findViewById(R.id.query_list_item_text);
        TextView unvisited = (TextView) convertView.findViewById(R.id.query_list_item_unvisited);
        // Populate the data into the template view using the data object
        itemText.setText(query.getTitle());
        unvisited.setText(String.valueOf(query.getUnvisitedCount()));
        // Return the completed view to render on screen
        return convertView;
    }
}
