package com.example.sondagecoincafe.bll.impl;

import com.example.sondagecoincafe.bll.ItemService;
import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.dal.CategoryDao;
import com.example.sondagecoincafe.dal.ItemDao;
import com.example.sondagecoincafe.dal.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemDao itemDao;
    private CategoryDao categoryDao;
    private UserDao userDao;

    public ItemServiceImpl(ItemDao itemDao, CategoryDao categoryDao, UserDao userDao) {
        this.itemDao = itemDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }

    @Override
    public List<Item> getAllItems() {return itemDao.findAll();}

    @Override
    public List<Item> findItemsInProgress() {return itemDao.findItemsInProgress();}

    @Override
    public List<Item> getAllItemsByCategoryId(int noCategory) {return itemDao.findItemsByCategory(noCategory);}

    @Override
    public void createItemWithCategory(Item item) {
        itemDao.create(item);
    }
}
