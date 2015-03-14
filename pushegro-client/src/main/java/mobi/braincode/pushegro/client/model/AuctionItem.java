package mobi.braincode.pushegro.client.model;


import java.util.Calendar;

public class AuctionItem {
    private String title;
    private boolean important;
    private Calendar lastChanged;
    private String price;

    public AuctionItem(String title, boolean important, Calendar lastChanged, String price) {
        this.title = title;
        this.important = important;
        this.lastChanged = lastChanged;
        this.price = price;
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
