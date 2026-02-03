package com.smorenolopez.lostandfoundbackend.repositories;

import com.smorenolopez.lostandfoundbackend.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
