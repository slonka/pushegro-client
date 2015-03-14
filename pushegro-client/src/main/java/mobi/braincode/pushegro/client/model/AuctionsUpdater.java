package mobi.braincode.pushegro.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slonka on 14.03.15.
 */
public class AuctionsUpdater {

    List<AuctionUpdate> getDifference(List<AuctionItem> localItems, List<AuctionItem> remoteItems) {
        Map<Long, AuctionItem> auctionUpdates = new HashMap<>();
        List<AuctionUpdate> result = new ArrayList<>();

        for(AuctionItem item : localItems) {
            auctionUpdates.put(item.getId(), item);
        }

        for(AuctionItem remoteItem : remoteItems) {
            if(auctionUpdates.containsKey(remoteItem.getId())) {
                AuctionItem localAuctionItem = auctionUpdates.get(remoteItem.getId());
                if(!localAuctionItem.equals(remoteItem)) {
                    result.add(new AuctionUpdate(AuctionStatus.MODIFIED, remoteItem));
                }
            } else {
                result.add(new AuctionUpdate(AuctionStatus.NEW, remoteItem));
            }
        }

        return result;
    }
}
