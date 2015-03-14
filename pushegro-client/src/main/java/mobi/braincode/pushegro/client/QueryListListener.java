package mobi.braincode.pushegro.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import mobi.braincode.pushegro.client.model.QueryItem;

import java.util.List;

/**
 * Created by slonka on 14.03.15.
 */
public class QueryListListener implements View.OnClickListener {

    private final QueryListAdapter queryListAdapter;
    private Activity activity;
    private List<QueryItem> queryItems;


    public QueryListListener(QueryListAdapter queryListAdapter, Activity activity, List<QueryItem> queryItems) {
        this.queryListAdapter = queryListAdapter;
        this.activity = activity;
        this.queryItems = queryItems;
    }


    @Override
    public void onClick(View v) {

        // Set up the input
        final EditText input = new EditText(v.getContext());

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setPadding(20, 0, 20, 20);
        input.setHint("Tytuł aukcji");

        new AlertDialog.Builder(v.getContext())
                .setTitle("Wyszukiwanie")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                .setView(input)
                        // Set the action buttons
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        activity.setContentView(R.layout.activity_query_list);
                        String text = input.getText().toString();
                        QueryItem queryItem = new QueryItem(text, 0);
                        queryItems.add(queryItem);
                        queryListAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
    }
}
