package com.example.sondagecoincafe.dal;

import com.example.sondagecoincafe.bo.Collect;

import java.util.Optional;

public interface CollectDao {
    void create(Collect collect);
    Optional<Collect> read(int idItem);
}
