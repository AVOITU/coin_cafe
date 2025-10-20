package com.example.sondagecoincafe.bll;

import com.example.sondagecoincafe.bo.Item;

import java.util.List;

public interface ItemService {

    List<Item> getAllItems();
    List<Item> findItemsInProgress();
    List<Item> getAllItemsByCategoryId(int noCategory);
    void createItemWithCategory(Item item);
}
