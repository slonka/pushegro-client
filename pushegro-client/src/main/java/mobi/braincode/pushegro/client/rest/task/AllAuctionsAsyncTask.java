package mobi.braincode.pushegro.client.rest.task;

import android.os.AsyncTask;
import mobi.braincode.pushegro.client.QueryListActivity;
import mobi.braincode.pushegro.client.model.Auction;
import mobi.braincode.pushegro.client.model.AuctionItem;
import mobi.braincode.pushegro.client.rest.RestFacade;

import java.util.*;

public class AllAuctionsAsyncTask extends AsyncTask<Void, Void, Map<String, List<AuctionItem>>> {

    private final String username;
    private final List<String> predicateIds;
    private QueryListActivity queryListActivity;

    public AllAuctionsAsyncTask(String username, List<String> predicateIds, QueryListActivity queryListActivity) {
        this.username = username;
        this.predicateIds = predicateIds;
        this.queryListActivity = queryListActivity;
    }

    @Override
    protected Map<String, List<AuctionItem>> doInBackground(Void... params) {
        Map<String, List<AuctionItem>> auctionItems = new HashMap<>();
        for (final String predicateId : predicateIds) {
            ArrayList<AuctionItem> predicateAuctions = new ArrayList<>();
            List<Auction> auctions = RestFacade.getAuctions(username, predicateId);

            for (Auction auction : auctions) {
                predicateAuctions.add(convertToAuctionItem(auction));
            }

            auctionItems.put(predicateId, predicateAuctions);
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
    protected void onPostExecute(Map<String, List<AuctionItem>> auctionsByPredicate) {
        super.onPostExecute(auctionsByPredicate);
        queryListActivity.updateAuctions(auctionsByPredicate);
    }


}
