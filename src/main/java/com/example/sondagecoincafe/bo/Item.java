package com.example.sondagecoincafe.bo;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Objects;

public class Item {

    private int idItem;

    @NotBlank
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères.")
    private String nameItem;

    @Size(min = 5, max = 300, message = "La description doit être comprise entre 5 et 300 caractères.")
    private String description;

    @NotNull
    @Future(message = "Vous devez mettre une date postérieure à aujourd'hui " +
            "pour laisser aux acheteurs le temps de se préparer")
    private LocalDate startingDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Positive(message = "Le prix doit être positif")
    private int startingPrice;


    @Min(value = 0, message = "Le prix doit être positif ou nul")
    private int sellPrice=0;

    
    private String status;

    private Category category;

    //@NotNull
    //TODO: décommenter
    private User owner;

    private User buyer;

    private Collect collect;

    public Item() {}

    public Item(int idItem, String nameItem, String description, LocalDate startingDate, LocalDate endDate,
                int startingPrice, int sellPrice, String status, User user, Category category, User owner, User buyer, Collect collect) {
        this.idItem = idItem;
        this.nameItem = nameItem;
        this.description = description;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.sellPrice = sellPrice;
        this.status = status;
        this.category = category;
        this.owner = owner;
        this.buyer = buyer;
        this.collect = collect;
    }

    public Item(String nameItem, String description, LocalDate startingDate, LocalDate endDate,
                int startingPrice, int sellPrice, String status, Category category, User owner, User buyer, Collect collect) {
        this.nameItem = nameItem;
        this.description = description;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.sellPrice = sellPrice;
        this.status = status;
        this.category = category;
        this.owner = owner;
        this.buyer = buyer;
        this.collect = collect;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Collect getCollect() {
        return collect;
    }

    public void setCollect(Collect collect) {
        this.collect = collect;
    }

    @Override
    public String toString() {
        return "Item{" +
                "idItem=" + idItem +
                ", nameItem='" + nameItem + '\'' +
                ", description='" + description + '\'' +
                ", startingDate=" + startingDate +
                ", endDate=" + endDate +
                ", startingPrice=" + startingPrice +
                ", sellPrice=" + sellPrice +
                ", status='" + status + '\'' +
                ", category=" + category +
                ", owner=" + owner +
                ", buyer=" + buyer +
                ", collect=" + collect +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return idItem == item.idItem && startingPrice == item.startingPrice && sellPrice == item.sellPrice && Objects.equals(nameItem, item.nameItem) && Objects.equals(description, item.description) && Objects.equals(startingDate, item.startingDate) && Objects.equals(endDate, item.endDate) && Objects.equals(status, item.status) && Objects.equals(category, item.category) && Objects.equals(owner, item.owner) && Objects.equals(buyer, item.buyer) && Objects.equals(collect, item.collect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItem, nameItem, description, startingDate, endDate, startingPrice, sellPrice, status, category, owner, buyer, collect);
    }
}
