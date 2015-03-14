package mobi.braincode.pushegro.client.persistence;

import mobi.braincode.pushegro.client.model.AuctionItem;
import mobi.braincode.pushegro.client.model.QueryItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryRepository {
    public static List<QueryItem> queryItems = new ArrayList<>();

    public static void addQuery(QueryItem item) {
        queryItems.add(item);
    }

    public static void removeQuery(QueryItem item) {
        queryItems.remove(item);
    }

    public static void removeQuery(int queryId) {
        Iterator<QueryItem> queryItemIterator = queryItems.iterator();

        while (queryItemIterator.hasNext()) {
            QueryItem item = queryItemIterator.next();
            if (item.getId() == queryId) {
                queryItemIterator.remove();
            }
        }
    }

    public static QueryItem findById(String queryId) {
        for (QueryItem queryItem : queryItems) {
            if (queryItem.getId() == Integer.valueOf(queryId)) {
                return                queryItem;
            }
        }
        return null;
    }

    public static void markAsSeen(String queryListId, Long auctionId) {
        QueryItem queryItem = QueryRepository.findById(queryListId);

        if (queryItem != null) {
            for (AuctionItem auctionItem : queryItem.getAuctionItems()) {
                if (auctionItem.getId() == auctionId) {
                    if (!auctionItem.isViewed()) {
                        auctionItem.markViewed();
                        queryItem.decreaseUnseen();
                    }
                }
            }
        }
    }
}
