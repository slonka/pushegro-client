package mobi.braincode.pushegro.client;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.software.shell.fab.ActionButton;
import mobi.braincode.pushegro.client.model.QueryItem;

import java.util.ArrayList;


public class QueryListActivity extends ActionBarActivity {

    ActionButton addButton;
    ArrayList<QueryItem> queryItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final QueryListAdapter queryListAdapter = new QueryListAdapter(this, queryItems);
        QueryListListener queryListListener = new QueryListListener(queryListAdapter, this, queryItems);

        if(queryItems.isEmpty()) {
            setContentView(R.layout.activity_empty_query_list);

            Button button = (Button) findViewById(R.id.empty_auctions_search);
            button.setOnClickListener(queryListListener);
        } else {
            setContentView(R.layout.activity_query_list);

            ListView listView = (ListView) findViewById(R.id.query_list);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    queryItems.remove(position);
                    queryListAdapter.notifyDataSetChanged();
                    return true;
                }
            });

            listView.setAdapter(queryListAdapter);

            addButton = (ActionButton) findViewById(R.id.query_list_add_query);
            addButton.setOnClickListener(queryListListener);
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


}
