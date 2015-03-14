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

    int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnvisitedCount(int unvisitedCount) {
        this.unvisitedCount = unvisitedCount;
    }

    String title;
    int unvisitedCount;

    public QueryItem(int id, String title, int unvisitedCount) {
        this.id = id;
        this.title = title;
        this.unvisitedCount = unvisitedCount;
    }

    public int getId() {
        return id;
    }
}
