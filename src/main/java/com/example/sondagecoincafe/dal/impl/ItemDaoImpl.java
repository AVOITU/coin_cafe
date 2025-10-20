package com.example.sondagecoincafe.dal.impl;

import com.example.sondagecoincafe.bo.Category;
import com.example.sondagecoincafe.bo.Collect;
import com.example.sondagecoincafe.bo.Item;
import com.example.sondagecoincafe.bo.User;
import com.example.sondagecoincafe.dal.ItemDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao {

    private static final String INSERT_CATEGORIE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres,\n" +
            "                             date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)\n" +
            "VALUES(:nom_article, :description, :date_debut_encheres,\n" +
            "       :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie);";
    private static final String SELECT_BY_NO_ARTICLE = "SELECT * FROM ARTICLES_VENDUS AS AV\n" +
            "                  INNER JOIN UTILISATEURS AS US ON US.no_utilisateur = AV.no_utilisateur\n" +
            "                  INNER JOIN CATEGORIES AS C ON C.no_categorie = AV.no_categorie\n" +
            "                  LEFT JOIN RETRAIT AS R ON AV.no_article = R.no_article\n" +
            "WHERE AV.no_article = :noArticle";
    private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS AS AV\n" +
            "    INNER JOIN UTILISATEURS AS US ON US.no_utilisateur = AV.no_utilisateur\n" +
            "    INNER JOIN CATEGORIES AS C ON C.no_categorie = AV.no_categorie\n" +
            "    LEFT JOIN RETRAIT AS R ON AV.no_article = R.no_article";
    private static final String SELECT_NAME = "SELECT * FROM ARTICLES_VENDUS AS AV\n" +
            "    INNER JOIN UTILISATEURS AS US ON US.no_utilisateur = AV.no_utilisateur\n" +
            "    INNER JOIN CATEGORIES AS C ON C.no_categorie = AV.no_categorie\n" +
            "    LEFT JOIN RETRAIT AS R ON AV.no_article = R.no_article\n" +
            "    WHERE AV.no_article = :idItem;";
    private static final String SELECT_ITEMS_BY_CATEGORY="SELECT * FROM ARTICLES_VENDUS AS AV\n" +
            "    INNER JOIN CATEGORIES AS C on C.no_categorie = AV.no_categorie\n" +
            "    INNER JOIN UTILISATEURS U on AV.no_utilisateur = U.no_utilisateur\n" +
            "    LEFT JOIN RETRAIT AS R ON AV.no_article = R.no_article\n" +
            "    WHERE C.no_categorie=? AND AV.date_debut_encheres<=GETDATE() AND AV.date_fin_encheres>getdate();";

    private static final String SELECT_ALL_ENCOURS="SELECT * FROM ARTICLES_VENDUS AS AV\n" +
            "            INNER JOIN UTILISATEURS AS US ON US.no_utilisateur = AV.no_utilisateur\n" +
            "            INNER JOIN CATEGORIES AS C ON C.no_categorie = AV.no_categorie\n" +
            "            LEFT JOIN RETRAIT AS R ON AV.no_article = R.no_article\n" +
            "            WHERE AV.date_debut_encheres<=GETDATE() AND AV.date_fin_encheres>getdate();";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public ItemDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item read(int no_article) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("noArticle", no_article);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_NO_ARTICLE,
                namedParameters,
                new ItemRowMapper()
        );
    }

    @Override
    public List<Item> findAll() {
        List<Item> item = jdbcTemplate.query(
                SELECT_ALL,
                new ItemRowMapper()
        );
        return item;
    }

    @Override
    public String findName(int no_article) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("idItem", no_article );

        Item item = namedParameterJdbcTemplate.queryForObject(
                SELECT_NAME, namedParameters,
                new ItemRowMapper()
        );
        return item.getNameItem();
    }

    @Override
    public void create(Item item) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("nom_article", item.getNameItem());
        namedParameters.addValue("description", item.getDescription());
        namedParameters.addValue("date_debut_encheres", item.getStartingDate());
        namedParameters.addValue("date_fin_encheres", item.getEndDate());
        namedParameters.addValue("prix_initial", item.getStartingPrice());
        namedParameters.addValue("prix_vente", item.getSellPrice());
        namedParameters.addValue("no_utilisateur", item.getOwner().getNo_user());
        namedParameters.addValue("no_categorie", item.getCategory().getNoCategory());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                INSERT_CATEGORIE,
                namedParameters,
                keyHolder
        );

        if (keyHolder.getKey() != null) {
            item.setIdItem((int) keyHolder.getKey().longValue());
        }
    }

    @Override
    public List<Item> findItemsByCategory(int noCategory) {

        return jdbcTemplate.query(SELECT_ITEMS_BY_CATEGORY, new ItemRowMapper() , noCategory);
    }

    @Override
    public List<Item> findItemsInProgress() {

        return jdbcTemplate.query(SELECT_ALL_ENCOURS, new ItemRowMapper());
    }

    class ItemRowMapper implements RowMapper<Item> {


        @Override
        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item();
            item.setIdItem(rs.getInt("no_article"));
            item.setNameItem(rs.getString("nom_article"));
            item.setDescription(rs.getString("description"));
            item.setStartingDate(rs.getDate("date_debut_encheres").toLocalDate());
            item.setEndDate(rs.getDate("date_fin_encheres").toLocalDate());
            item.setStartingPrice(rs.getInt("prix_initial"));
            item.setSellPrice(rs.getInt("prix_vente"));
            item.setstatus(rs.getString("etat_vente"));

            User user = new User();
            user.setNo_user(rs.getInt("no_utilisateur"));
            user.setUsername(rs.getString("pseudo"));
            user.setName(rs.getString("nom"));
            user.setFirstname(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("telephone"));
            user.setStreet(rs.getString("rue"));
            user.setZipCode(rs.getString("code_postal"));
            user.setCity(rs.getString("ville"));
            user.setCredit(rs.getInt("credit"));
            user.setAdmin(rs.getBoolean("administrateur"));

            item.setOwner(user);

            Category category=new Category();
            category.setNoCategory(rs.getInt("no_categorie"));
            category.setName(rs.getString("libelle"));

            item.setCategory(category);

            Collect collect=new Collect();
            collect.setId(rs.getInt("id"));
            collect.setStreet(rs.getString("rue"));
            collect.setZipCode(rs.getString("code_postal"));
            collect.setCity(rs.getString("ville"));

            item.setCollect(collect);

            return item;
        }
    }
}
