package mobi.braincode.pushegro.client;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import mobi.braincode.pushegro.client.model.AuctionItem;

import java.util.List;

public class AuctionListAdapter extends ArrayAdapter<AuctionItem> {
    public AuctionListAdapter(Context context, List<AuctionItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AuctionItem auction = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_auction_list_item, parent, false);
        }
        // Lookup view for data population
        TextView titleTextView = (TextView) convertView.findViewById(R.id.auction_list_item_title);
        TextView priceTextView = (TextView) convertView.findViewById(R.id.auction_list_item_price);
        TextView lastChangedTextView = (TextView) convertView.findViewById(R.id.auction_list_item_last_changed);
        ImageButton importantImageButton = (ImageButton) convertView.findViewById(R.id.auction_list_item_important);

        CharSequence lastChanged = DateUtils.getRelativeTimeSpanString(auction.getLastChanged().getTimeInMillis());

        titleTextView.setText(auction.getTitle());
        priceTextView.setText(auction.getPrice());
        lastChangedTextView.setText(lastChanged);
        importantImageButton.setOnClickListener(new OnImportantClickListener(auction));

        if (auction.isImportant()) {
            importantImageButton.setImageResource(R.drawable.auction_favorite_star);
        }

        return convertView;
    }

    private class OnImportantClickListener implements View.OnClickListener {

        private AuctionItem auction;

        public OnImportantClickListener(AuctionItem auction) {
            this.auction = auction;
        }

        @Override
        public void onClick(View v) {
            auction.flipImportance();
            ImageButton importantImageButton = (ImageButton) v;
            importantImageButton.setImageResource(auction.isImportant() ? R.drawable.auction_favorite_star : R.drawable.auction_favorite_star_gray);
        }
    }
}
