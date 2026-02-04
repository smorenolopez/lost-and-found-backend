package com.smorenolopez.lostandfoundbackend.controllers;

import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;
import com.smorenolopez.lostandfoundbackend.services.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    @DisplayName("Creates new item")
    void createItem_whenPostRequestWithItem_shouldCreateNewItem() throws Exception {
        // Given
        ItemDTO itemDTO = this.createItemDTO();
        when(this.itemService.createItem(itemDTO)).thenReturn(this.createItemResponse());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(itemDTO));

        // When
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        // Then
        ItemResponse itemResponse = new ObjectMapper()
                .readValue(
                        result.getResponse().getContentAsString(),
                        ItemResponse.class
                );
        assertNotNull(itemResponse);
        assertNotNull(itemResponse.getId());
        assertNotNull(itemResponse.getDescription());
        assertEquals("This is an ItemDTO test object", itemResponse.getDescription());

    }

    @Test
    @DisplayName("Returns all items")
    void getItems_whenGetRequest_shouldReturnItems() throws Exception {
        // Given
        when(this.itemService.findAllItems()).thenReturn(this.createItemResponses());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/items")
                .accept(MediaType.APPLICATION_JSON);

        // When
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        List<ItemResponse> responses = new ObjectMapper()
                .readValue(
                        result.getResponse().getContentAsString(),
                        new TypeReference<>() {}
                );
        // Then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("Item response 1", responses.get(0).getDescription());
        assertEquals(2L, responses.get(1).getId());
        assertEquals("Item response 2", responses.get(1).getDescription());
        assertEquals(3L, responses.get(2).getId());
        assertEquals("Item response 3", responses.get(2).getDescription());
    }


    private ItemDTO createItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescription("This is a test item");
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