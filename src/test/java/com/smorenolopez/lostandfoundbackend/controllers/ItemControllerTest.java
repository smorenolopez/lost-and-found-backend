package com.smorenolopez.lostandfoundbackend.controllers;

import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;
import com.smorenolopez.lostandfoundbackend.services.ItemService;
import com.smorenolopez.lostandfoundbackend.services.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

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

    private ItemDTO createItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescription("This is a ItemDTO test object");
        return itemDTO;
    }

    private ItemResponse createItemResponse() {
        return new ItemResponse(1L, "This is an ItemDTO test object");
    }

}