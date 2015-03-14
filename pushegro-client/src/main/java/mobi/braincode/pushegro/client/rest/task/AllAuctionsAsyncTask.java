package mobi.braincode.pushegro.client.rest.task;

import android.os.AsyncTask;
import mobi.braincode.pushegro.client.QueryListActivity;
import mobi.braincode.pushegro.client.model.AuctionItem;
import mobi.braincode.pushegro.client.rest.RestFacade;

import java.util.ArrayList;
import java.util.List;

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
        final List<AuctionItem> auctionItems = new ArrayList<>();
        for (final String predicateId : predicateIds) {
            List<AuctionItem> auctions = RestFacade.getAuctions(username, predicateId);
            auctionItems.addAll(auctions);
        }
        return auctionItems;
    }

    @Override
    protected void onPostExecute(List<AuctionItem> auctionItems) {
        super.onPostExecute(auctionItems);
        queryListActivity.updateAuctions(auctionItems);
    }
}
