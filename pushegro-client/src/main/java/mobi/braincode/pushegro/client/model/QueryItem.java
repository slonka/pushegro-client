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

    String title;
    int unvisitedCount;
    int id;
    List<AuctionItem> auctionItemList = new ArrayList<>();

    public void setUnvisitedCount(int unvisitedCount) {
        this.unvisitedCount = unvisitedCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AuctionItem> getAuctionItemList() {
        return auctionItemList;
    }

    public QueryItem(String title, int unvisitedCount) {
        this.title = title;
        this.unvisitedCount = unvisitedCount;
    }

    public void addAuctionItemList(AuctionItem auctionItem) {
        auctionItemList.add(auctionItem);
    }
}
