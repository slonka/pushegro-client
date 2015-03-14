package mobi.braincode.pushegro.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slonka on 14.03.15.
 */
public class QueryItem {
    public String getTitle() {
        return title;
    }

    public int getUnvisitedCount() {
        return unvisitedCount;
    }

    public List<AuctionItem> getAuctionItems() {
        return auctionItems;
    }

    int id;
    String title;
    int unvisitedCount;
    List<AuctionItem> auctionItems;

    public QueryItem(int id, String title, int unvisitedCount) {
        this.id = id;
        this.title = title;
        this.unvisitedCount = unvisitedCount;
        this.auctionItems = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
}
