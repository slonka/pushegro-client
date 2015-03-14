package mobi.braincode.pushegro.client.model;

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

    public QueryItem(String title, int unvisitedCount) {
        this.title = title;
        this.unvisitedCount = unvisitedCount;
    }
}
