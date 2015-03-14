package mobi.braincode.pushegro.client;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import mobi.braincode.pushegro.client.model.AuctionItem;

import java.util.ArrayList;
import java.util.Calendar;


public class AuctionListActivity extends ActionBarActivity {

    private ListView auctionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<AuctionItem> auctions = getAuctionItems();

        if (auctions.isEmpty()) {
            setContentView(R.layout.activity_empty_auction_list);
        } else {
            setContentView(R.layout.activity_auction_list);
            AuctionListAdapter adapter = new AuctionListAdapter(this, auctions);
            auctionListView = (ListView) findViewById(R.id.auction_list_view);
            auctionListView.setAdapter(adapter);
        }
    }

    private ArrayList<AuctionItem> getAuctionItems() {
        ArrayList<AuctionItem> auctions = new ArrayList<>();
        AuctionItem item1 = new AuctionItem("Maczeta JP2", true, Calendar.getInstance(), "99999 PLN");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        AuctionItem item2 = new AuctionItem("Sztacheta JP3", false, cal, "199999 PLN");

        auctions.add(item1);
        auctions.add(item2);

        return auctions;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auction_list, menu);
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
