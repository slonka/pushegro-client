package mobi.braincode.pushegro.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.software.shell.fab.ActionButton;
import mobi.braincode.pushegro.client.model.QueryItem;

import java.util.ArrayList;
import java.util.List;


public class QueryListActivity extends ActionBarActivity {

    List<QueryItem> queryItems;
    QueryListAdapter queryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queryItems = new ArrayList<>();
//        queryItems.add(new QueryItem("Maczeta JP2", 1));
//        queryItems.add(new QueryItem("Komiksy Dragon Ball", 2));
        queryListAdapter = new QueryListAdapter(this, queryItems);

        refreshActivity();
    }

    private void refreshActivity() {
        if (queryItems.isEmpty()) {
            setContentView(R.layout.activity_empty_query_list);
            Button emptyAddButton = (Button) findViewById(R.id.empty_query_list_search);
            emptyAddButton.setOnClickListener(new OnAddButtonClickListener(queryListAdapter));

        } else {
            setContentView(R.layout.activity_query_list);
            ListView listView = (ListView) findViewById(R.id.query_list);
            listView.setOnItemLongClickListener(new OnQueryLongClickListener(queryListAdapter));
            listView.setAdapter(queryListAdapter);

            ActionButton addButton = (ActionButton) findViewById(R.id.query_list_add_query);
            addButton.setOnClickListener(new OnAddButtonClickListener(queryListAdapter));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class OnQueryLongClickListener implements AdapterView.OnItemLongClickListener {

        private QueryListAdapter queryListAdapter;

        private OnQueryLongClickListener(QueryListAdapter queryListAdapter) {
            this.queryListAdapter = queryListAdapter;
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            queryListAdapter.remove(queryListAdapter.getItem(position));
            queryListAdapter.notifyDataSetChanged();
            refreshActivity();
            return true;
        }
    }

    private class OnAddButtonClickListener implements View.OnClickListener {

        QueryListAdapter queryListAdapter;

        public OnAddButtonClickListener(QueryListAdapter queryListAdapter) {
            this.queryListAdapter = queryListAdapter;
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
                            String text = input.getText().toString();
                            QueryItem queryItem = new QueryItem(text, 0);
                            queryListAdapter.add(queryItem);
                            queryListAdapter.notifyDataSetChanged();
                            refreshActivity();
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
}
