package com.smorenolopez.lostandfoundbackend.services;

import com.smorenolopez.lostandfoundbackend.models.Item;
import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;
import com.smorenolopez.lostandfoundbackend.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemServiceImplTest {

    private static ItemService itemService;

    @BeforeAll
    static void setUpItemService() {
        ItemRepository itemRepository = mock(ItemRepository.class);
        when(itemRepository.findAll()).thenReturn(createItems());
        when(itemRepository.save(any(Item.class))).thenReturn(createSavedItem());
        itemService = new ItemServiceImpl(
                itemRepository,
                new ModelMapper()
        );
    }

    @Test
    void findAllItems_shouldReturnAllItems() {
        // Given
        // When
        List<ItemResponse> responses = itemService.findAllItems();

        // Then
        assertNotNull(responses);
        assertEquals(1L, responses.get(0).getId());
        assertEquals("This is item 1", responses.get(0).getDescription());
        assertEquals(2L, responses.get(1).getId());
        assertEquals("This is item 2", responses.get(1).getDescription());
        assertEquals(3L, responses.get(2).getId());
        assertEquals("This is item 3", responses.get(2).getDescription());
    }

    @Test
    void createItem_shouldReturnNewItem() {
        // Given
        // When
        ItemResponse response = itemService.createItem(new ItemDTO(1L, "This is item 1"));

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("This is item 1", response.getDescription());
    }

    private static List<Item> createItems() {
        return List.of(
                new Item(1L, "This is item 1", "abcd"),
                new Item(2L, "This is item 2", "efgh"),
                new Item(3L, "This is item 3", "ijkl")
        );
    }

    private static Item createSavedItem() {
        return new Item(1L, "This is item 1", "abcd");
    }

}