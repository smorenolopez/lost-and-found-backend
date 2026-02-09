package com.smorenolopez.lostandfoundbackend.services;

import com.smorenolopez.lostandfoundbackend.exceptions.ItemNotFoundException;
import com.smorenolopez.lostandfoundbackend.models.Item;
import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;
import com.smorenolopez.lostandfoundbackend.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ItemResponse> findAllItems() {
        return this.itemRepository.findAll().stream()
                .map(item -> this.modelMapper.map(item, ItemResponse.class))
                .toList();
    }

    @Override
    public ItemResponse findItemById(Long id) {
        Item item = this.itemRepository.findItemById(id).orElseThrow(
                () -> new ItemNotFoundException(id)
        );
        return this.modelMapper.map(item, ItemResponse.class);
    }

    @Override
    public ItemResponse createItem(ItemDTO itemDTO) {
        Item item = this.modelMapper.map(itemDTO, Item.class);
        Item savedItem = this.itemRepository.save(item);
        return this.modelMapper.map(savedItem, ItemResponse.class);
    }
}
