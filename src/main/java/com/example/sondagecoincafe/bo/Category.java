package com.example.sondagecoincafe.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

public class Category {
    private int noCategory;

    @NotBlank
    @Size(max=30, message = "Pas plus de 30 caractères pour créer un catégorie")
    private String name;

    private List<Item> items;

    public Category() {
    }

    public Category(int noCategory, String name, List<Item> items) {
        this.noCategory = noCategory;
        this.name = name;
        this.items = items;
    }

    public Category(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public int getNoCategory() {
        return noCategory;
    }

    public void setNoCategory(int noCategory) {
        this.noCategory = noCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return noCategory == category.noCategory && Objects.equals(name, category.name) && Objects.equals(items, category.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noCategory, name, items);
    }

    @Override
    public String toString() {
        return "Category{" +
                "noCategory=" + noCategory +
                ", name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
