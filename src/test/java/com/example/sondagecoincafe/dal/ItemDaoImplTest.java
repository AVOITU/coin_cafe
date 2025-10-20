package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Category;
import com.example.sondagecoincafe.bo.Collect;
import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.bo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemDaoImplTest {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        String sql = "DELETE FROM ARTICLES_VENDUS WHERE nom_article = 'createItemWithCategory'";
        jdbcTemplate.update(sql);
    }

    @Test
    public void createItemWithCategory(){
        Item item = new Item();

        User owner = new User();
        owner.setNo_user(18);

        Category category = new Category();
        category.setNoCategory(1);

        Collect collect = new Collect();
        collect.setId(2);

        item.setNameItem("createItemWithCategory");
        item.setDescription("test desc");
        item.setStartingDate(LocalDate.now().plusDays(1));
        item.setEndDate(LocalDate.now().plusDays(2));
        item.setStartingPrice(5);
        item.setSellPrice(0);
        item.setOwner(owner);
        item.setCategory(category);
        item.setCollect(collect);

        itemDao.create(item);

        List<Item> items=itemDao.findItemsByCategory(1);
        item=items.get(items.size()-1);
        assertThat(item.getNameItem()).isEqualTo("createItemWithCategory");
        assertThat(item.getDescription()).isEqualTo("test desc");
        assertThat(item.getStartingDate()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(item.getEndDate()).isEqualTo(LocalDate.now().plusDays(2));
        assertThat(item.getStartingPrice()).isEqualTo(5);
        assertThat(item.getSellPrice()).isEqualTo(0);
        assertThat(item.getOwner().getNo_user()).isEqualTo(18);
        assertThat(item.getCategory().getNoCategory()).isEqualTo(1);
        assertThat(item.getCollect().getId()).isEqualTo(0);//TODO
    }

    @Test
    public void findItemsByCategoryId(){
        int categoryId = 1;
        List<Item> items=itemDao.findItemsByCategory(categoryId);
        assertThat(items.size()).isEqualTo(7);
        assertThat(items.get(0).getNameItem()).isEqualTo("iPhone 13");
        assertThat(items.get(items.size()-1).getNameItem()).isEqualTo("Compact Printer");
    }

    @Test
    public void findName(){

        int no_article=5;
        String itemName=itemDao.findName(no_article);

        assertThat(itemName).isEqualTo("iPhone 13");
    }
}
