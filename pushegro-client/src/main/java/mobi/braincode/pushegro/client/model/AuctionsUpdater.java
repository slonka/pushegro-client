package mobi.braincode.pushegro.client.model;

import java.util.*;

/**
 * Created by slonka on 14.03.15.
 */
public class AuctionsUpdater {

    public static ArrayList<AuctionUpdate> getDifference(List<AuctionItem> localItems, List<AuctionItem> remoteItems) {
        Map<Long, AuctionItem> auctionUpdates = new HashMap<>();
        Set<AuctionItem> remoteItemSet = new HashSet<>();
        ArrayList<AuctionUpdate> result = new ArrayList<>();

        for(AuctionItem item : localItems) {
            auctionUpdates.put(item.getId(), item);
        }

        for(AuctionItem item : remoteItems) {
            remoteItemSet.add(item);
        }

        for(AuctionItem localItem: localItems) {
            if(!remoteItems.contains(localItem)) {
                result.add(new AuctionUpdate(AuctionStatus.DELETED, localItem));
            }
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
