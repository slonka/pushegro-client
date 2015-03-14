package mobi.braincode.pushegro.client.rest.task;

import android.os.AsyncTask;
import mobi.braincode.pushegro.client.QueryListActivity;
import mobi.braincode.pushegro.client.model.AuctionItem;
import mobi.braincode.pushegro.client.rest.RestFacade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Grzegorz Miejski (SG0221133) on 3/14/2015.
 */
public class AllAuctionsAsyncTask extends AsyncTask<Void, Void, List<AuctionItem>> {

    private final String username;
    private final List<String> predicateIds;
    private QueryListActivity queryListActivity;

    public AllAuctionsAsyncTask(String username, List<String> predicateIds, QueryListActivity queryListActivity) {
        this.username = username;
        this.predicateIds = predicateIds;
        this.queryListActivity = queryListActivity;
    }

    @Override
    protected List<AuctionItem> doInBackground(Void... params) {
        final List<AuctionItem> auctionItems = Collections.synchronizedList(new ArrayList<AuctionItem>());

        for (final String predicateId : predicateIds) {
            new AsyncTask<Void, Void, List<AuctionItem>>() {
                @Override
                protected List<AuctionItem> doInBackground(Void... params) {
                    return RestFacade.getAuctions(username, predicateId);
                }

                @Override
                protected void onPostExecute(List<AuctionItem> auctions) {
                    super.onPostExecute(auctions);
                    auctionItems.addAll(auctions);
                }
            }.execute();
        }
        return auctionItems;
    }

    @Override
    protected void onPostExecute(List<AuctionItem> auctionItems) {
        super.onPostExecute(auctionItems);
        queryListActivity.updateAuctions(auctionItems);
    }
}
