package mobi.braincode.pushegro.client;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;
import com.hudomju.swipe.adapter.ViewAdapter;
import com.software.shell.fab.ActionButton;
import mobi.braincode.pushegro.client.gcm.GcmIntentService;
import mobi.braincode.pushegro.client.model.AuctionItem;
import mobi.braincode.pushegro.client.model.AuctionStatus;
import mobi.braincode.pushegro.client.model.AuctionUpdate;
import mobi.braincode.pushegro.client.model.QueryItem;
import mobi.braincode.pushegro.client.persistence.QueryRepository;
import mobi.braincode.pushegro.client.repository.SharedPreferencesFacade;
import mobi.braincode.pushegro.client.repository.SharedPreferencesProperties;
import mobi.braincode.pushegro.client.rest.RestFacade;
import mobi.braincode.pushegro.client.rest.task.AllAuctionsAsyncTask;

import java.io.IOException;
import java.util.*;

import static mobi.braincode.pushegro.client.repository.SharedPreferencesProperties.PROPERTY_USERNAME;


public class QueryListActivity extends ActionBarActivity {

    private static List<QueryItem> queryItems = new ArrayList<>();
    QueryListAdapter queryListAdapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        queryListAdapter = new QueryListAdapter(this, queryItems);

        Intent intent = getIntent();
        String predicatesToUpdate = intent.getStringExtra(GcmIntentService.SERVER_MESSAGE);
        if (predicatesToUpdate != null) {
            List<String> queryIds = Arrays.asList(predicatesToUpdate.split(","));
            String username = SharedPreferencesFacade.getString(getApplicationContext(), SharedPreferencesProperties.PROPERTY_USERNAME);
            new AllAuctionsAsyncTask(username, queryIds, this).execute();
        }

        refreshActivity();

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    private void refreshActivity() {
        if (queryItems.isEmpty()) {
            setContentView(R.layout.activity_empty_query_list);
            Button emptyAddButton = (Button) findViewById(R.id.empty_query_list_search);
            emptyAddButton.setOnClickListener(new OnAddButtonClickListener(queryListAdapter));

        } else {
            setContentView(R.layout.activity_query_list);

            ListView listView = (ListView) findViewById(R.id.query_list);

            final SwipeToDismissTouchListener touchListener =
                    new SwipeToDismissTouchListener(
                            new ListViewAdapter(listView),
                            new SwipeToDismissTouchListener.DismissCallbacks() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(ViewAdapter viewAdapter, final int i) {

                                    new AsyncTask<QueryItem, Void, Void>() {

                                        @Override
                                        protected Void doInBackground(QueryItem... params) {
                                            RestFacade.removeWatcher(SharedPreferencesProperties.PROPERTY_USERNAME, queryListAdapter.getItem(i).getId());
                                            return null;
                                        }

                                        @Override
                                        protected void onPostExecute(Void v) {
                                            Log.e(this.getClass().getSimpleName(), ">>>>>> Post execute");
                                            onCancelled();
                                            queryListAdapter.remove(queryListAdapter.getItem(i));
                                        }
                                    }.execute(queryListAdapter.getItem(i));
                                }
                            });

            listView.setOnTouchListener(touchListener);
            listView.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (touchListener.existPendingDismisses()) {
                        touchListener.undoPendingDismiss();
                    } else {
                        Intent intent = new Intent(QueryListActivity.this, AuctionListActivity.class);
                        startActivity(intent);
                    }
                }
            });
            listView.setOnItemLongClickListener(new OnQueryLongClickListener(queryListAdapter));
            listView.setAdapter(queryListAdapter);

            ActionButton addButton = (ActionButton) findViewById(R.id.query_list_add_query);
            addButton.setOnClickListener(new OnAddButtonClickListener(queryListAdapter));
        }
    }

    public void udpateQueryList(String queryId, List<AuctionItem> remoteAuctionItemList) {
        
    }

    public void updateAuctionList(ArrayList<AuctionUpdate> auctionUpdates, List<AuctionItem> auctionItems) {
        Iterator<AuctionUpdate> auctionUpdateIterator = auctionUpdates.iterator();

        for(AuctionUpdate auctionUpdate : auctionUpdates) {
            if(auctionUpdate.getAuctionStatus() == AuctionStatus.NEW) {
                auctionItems.add(auctionUpdate.getAuctionItem());
            } else {
                AuctionItem found = null;
                for(AuctionItem item : auctionItems) {
                    if(auctionUpdate.getAuctionItem().getId() == item.getId()) {
                        found = item;
                        break;
                    }
                }

                if (auctionUpdate.getAuctionStatus() == AuctionStatus.DELETED) {
                    auctionItems.remove(found);
                } else if (auctionUpdate.getAuctionStatus() == AuctionStatus.MODIFIED) {
                    found = auctionUpdate.getAuctionItem();
                }
            }
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
                            if (!text.trim().isEmpty()) {
                                // TODO add validation


                                final String username = SharedPreferencesFacade.getString(context, PROPERTY_USERNAME);


                                new AsyncTask<String, Void, QueryItem>() {
                                    ProgressDialog dialog;

                                    @Override
                                    protected void onPreExecute() {
                                        dialog = ProgressDialog.show(QueryListActivity.this, "Zapisywanie...", "", true, false);
                                    }

                                    @Override
                                    protected QueryItem doInBackground(String... params) {
                                        Log.e(this.getClass().getSimpleName(), ">>>>>> Pre add watcher");
                                        String rs = RestFacade.addWatcher(username, params[0]);
                                        //{ "keyword": "tootot", "predicateId": 32 }
                                        HashMap<String, Object> result =
                                                null;
                                        try {
                                            result = new org.codehaus.jackson.map.ObjectMapper().readValue(rs, HashMap.class);
                                        } catch (IOException e) {
                                            Log.e(this.getClass().getSimpleName(), "Cannot parse response: " + result, e);
                                        }
                                        Integer predicateId = Integer.valueOf(result.get("predicateId").toString());
                                        String keyword = result.get("keyword").toString();
                                        QueryItem queryItem = new QueryItem(predicateId, keyword, 0);
                                        Log.e(this.getClass().getSimpleName(), ">>>>>> Post add watcher");
                                        Log.e(this.getClass().getSimpleName(), ">>>>>> Received rs: " + rs);
                                        return queryItem;
                                    }

                                    @Override
                                    protected void onPostExecute(QueryItem queryItem) {
                                        Log.e(this.getClass().getSimpleName(), ">>>>>> Post execute");
                                        onCancelled();
                                        queryItems.add(queryItem);
                                        queryListAdapter.notifyDataSetChanged();
                                        QueryRepository.addQuery(queryItem);
                                        refreshActivity();
                                    }

                                    @Override
                                    protected void onCancelled() {
                                        Log.e(this.getClass().getSimpleName(), ">>>>>> Cancelled");
                                        if (dialog != null) dialog.dismiss();
                                    }

                                    @Override
                                    protected void onCancelled(QueryItem aVoid) {
                                        onCancelled();
                                    }
                                }.execute(text);


                            }
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

    public void updateAuctions(Map<String, List<AuctionItem>> auctions) {


    }

}
