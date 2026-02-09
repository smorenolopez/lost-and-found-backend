package com.smorenolopez.lostandfoundbackend.repositories;

import com.smorenolopez.lostandfoundbackend.models.Item;
import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemById(Long id);
}
