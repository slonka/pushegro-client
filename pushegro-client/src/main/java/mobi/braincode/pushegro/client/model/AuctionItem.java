package mobi.braincode.pushegro.client.model;


import java.util.Calendar;

public class AuctionItem {
    private String title;
    private long id;
    private boolean important;
    private Calendar lastChanged;
    private String price;
    private boolean removed;

    public AuctionItem(long id, String title, boolean important, Calendar lastChanged, String price) {
        this.title = title;
        this.id = id;
        this.important = important;
        this.lastChanged = lastChanged;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean isRemoved() {
        return removed;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionItem that = (AuctionItem) o;

        if (id != that.id) return false;
        if (lastChanged != null ? !lastChanged.equals(that.lastChanged) : that.lastChanged != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (lastChanged != null ? lastChanged.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
