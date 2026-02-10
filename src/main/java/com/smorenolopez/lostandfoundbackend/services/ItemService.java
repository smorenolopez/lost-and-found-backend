package com.smorenolopez.lostandfoundbackend.services;

import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;

import java.util.List;

public interface ItemService {
    List<ItemResponse> findAllItems();
    ItemResponse findItemById(Long id);
    ItemResponse createItem(ItemDTO itemDTO);
    ItemResponse updateItem(Long id, ItemDTO itemDTO);
}
