package com.smorenolopez.lostandfoundbackend.services;

import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;

import java.util.List;

public interface ItemService {
    public List<ItemResponse> findAllItems();
    public ItemResponse findItemById(Long id);
    public ItemResponse createItem(ItemDTO itemDTO);
}
