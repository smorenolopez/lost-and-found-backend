package com.smorenolopez.lostandfoundbackend.controllers;

import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;
import com.smorenolopez.lostandfoundbackend.services.ItemService;
import com.smorenolopez.lostandfoundbackend.services.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemControllerTest {

    @Test
    void createItem_shouldReturnNewItemResponse() {
        // Given
        ItemService itemService = mock(ItemServiceImpl.class);
        when(itemService.createItem(this.createItemDTO())).thenReturn(this.createItemResponse());
        ItemController itemController = new ItemController(itemService);

        // When
        ResponseEntity<ItemResponse> response = itemController.createItem(this.createItemDTO());

        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("This is an ItemDTO test object", response.getBody().getDescription());
    }

    @Test
    void getItems_shouldReturnItemResponses() {
        // Given
        ItemService itemService = mock(ItemServiceImpl.class);
        when(itemService.findAllItems()).thenReturn(this.createItemResponses());
        ItemController itemController = new ItemController(itemService);

        // When
        ResponseEntity<List<ItemResponse>> itemResponses = itemController.getItems();

        // Then
        assertNotNull(itemResponses);
        assertNotNull(itemResponses.getBody());
        assertEquals(1L ,itemResponses.getBody().get(0).getId());
        assertEquals("Item response 1",itemResponses.getBody().get(0).getDescription());
        assertEquals(2L ,itemResponses.getBody().get(1).getId());
        assertEquals("Item response 2" ,itemResponses.getBody().get(1).getDescription());
        assertEquals(3L ,itemResponses.getBody().get(2).getId());
        assertEquals("Item response 3" ,itemResponses.getBody().get(2).getDescription());
    }

    private ItemDTO createItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescription("This is a ItemDTO test object");
        return itemDTO;
    }

    private ItemResponse createItemResponse() {
        return new ItemResponse(1L, "This is an ItemDTO test object");
    }

    private List<ItemResponse> createItemResponses() {
        List<ItemResponse> itemResponses = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            itemResponses.add(new ItemResponse(i + 1, "Item response " + (i + 1)));
        }
        return itemResponses;
    }

}