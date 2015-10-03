package io.jari.materialup.responses;

import io.jari.materialup.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rsicarelli on 7/15/15.
 */
@Deprecated
public class ItemResponse {

    private List<Item> mItemList = new ArrayList<>();

    public List<Item> getItemList() {
        return mItemList;
    }

    public void setItemList(List<Item> itemList) {
        this.mItemList = itemList;
    }
}
