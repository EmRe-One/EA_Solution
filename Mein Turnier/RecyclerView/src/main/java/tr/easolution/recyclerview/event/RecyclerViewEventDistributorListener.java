package tr.easolution.recyclerview.event;

public interface RecyclerViewEventDistributorListener {
    void onAddedToEventDistributor(BaseRecyclerViewEventDistributor distributor);
    void onRemovedFromEventDistributor(BaseRecyclerViewEventDistributor distributor);
}
