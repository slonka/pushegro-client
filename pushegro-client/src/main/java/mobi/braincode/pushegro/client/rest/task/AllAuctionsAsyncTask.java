package mobi.braincode.pushegro.client.rest.task;

import android.os.AsyncTask;
import mobi.braincode.pushegro.client.QueryListActivity;
import mobi.braincode.pushegro.client.model.Auction;
import mobi.braincode.pushegro.client.model.AuctionItem;
import mobi.braincode.pushegro.client.rest.RestFacade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
            List<Auction> auctions = RestFacade.getAuctions(username, predicateId);
            for (Auction auction : auctions) {
                auctionItems.add(convertToAuctionItem(auction));
            }

            System.out.println();
//            auctionItems.addAll(auctions);
        }
        return auctionItems;
    }

    private AuctionItem convertToAuctionItem(Auction auction) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(auction.getEndDateTime());
        return new AuctionItem(auction.getAuctionId(), auction.getTitle(), false, calendar, String.valueOf(auction.getPrice()));
    }


    @Override
    protected void onPostExecute(List<AuctionItem> auctionItems) {
        super.onPostExecute(auctionItems);
        queryListActivity.updateAuctions(auctionItems);
    }


}
