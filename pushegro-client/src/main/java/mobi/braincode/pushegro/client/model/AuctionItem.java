package mobi.braincode.pushegro.client.model;


import java.util.Calendar;

public class AuctionItem {
    private String title;
    private long id;
    private boolean important;
    private Calendar lastChanged;
    private String price;

    public AuctionItem(String title, long id, boolean important, Calendar lastChanged, String price) {
        this.title = title;
        this.id = id;
        this.important = important;
        this.lastChanged = lastChanged;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isImportant() {
        return important;
    }

    public Calendar getLastChanged() {
        return lastChanged;
    }

    public String getPrice() {
        return price;
    }
}
