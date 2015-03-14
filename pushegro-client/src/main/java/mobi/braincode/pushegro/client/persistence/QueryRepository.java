package mobi.braincode.pushegro.client.persistence;

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
}
