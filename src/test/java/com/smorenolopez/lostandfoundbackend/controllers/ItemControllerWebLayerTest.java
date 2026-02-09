package com.smorenolopez.lostandfoundbackend.controllers;

import com.smorenolopez.lostandfoundbackend.exceptions.ItemNotFoundException;
import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;
import com.smorenolopez.lostandfoundbackend.services.ItemService;
import com.smorenolopez.lostandfoundbackend.services.ItemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
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
        assertEquals("This is an Item test object", itemResponse.getDescription());

    }

    @Test
    @DisplayName("Invalid request content: empty description")
    void createItem_whenPostRequestWithEmptyItemDescription_shouldReturnBadRequest() throws Exception {
        // Given
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescription("");
        when(this.itemService.createItem(itemDTO)).thenReturn(this.createItemResponse());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(itemDTO));

        // When
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        // Then
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Invalid request content.", result.getResponse().getErrorMessage());
    }

    @Test
    @DisplayName("Invalid request content: description too long")
    void createItem_whenPostRequestWithItemDescriptionGreaterThanMaxValue_shouldReturnBadRequest() throws Exception {
        // Given
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescription("""
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur et purus sagittis felis tempus finibus 
                sit amet at enim. Sed pellentesque, nisl at rutrum consectetur, augue sem euismod magna, eget malesuada 
                orci tellus sit amet odio. Vivamus eros augue, commodo scelerisque iaculis eget, tincidunt non quam. 
                Pellentesque ullamcorper aliquet velit, nec sodales turpis pretium a. In consequat nisi lobortis leo 
                interdum pretium. Praesent mattis fermentum purus id feugiat. Fusce porta orci lectus, non sagittis 
                tortor ullamcorper sed. Mauris dignissim enim tortor, molestie volutpat sapien ultrices et. Curabitur 
                facilisis vestibulum interdum.
                
                Suspendisse pulvinar commodo luctus. Nullam dictum quam nisi. In nec ipsum libero. In id felis at lorem 
                ornare eleifend ut vitae ipsum. Etiam rutrum sapien eu bibendum semper. Nulla viverra eros ac ultrices 
                venenatis. Praesent sit amet tellus at elit placerat tempus. In in orci quis magna varius egestas. 
                Nulla condimentum sit amet tortor quis volutpat. Duis convallis placerat quam id ornare. Donec elementum 
                lectus interdum lorem commodo euismod. Aenean id nisl libero. Orci varius natoque penatibus et magnis 
                dis parturient montes, nascetur ridiculus mus. Phasellus hendrerit mi sit amet massa dictum consequat. 
                Duis accumsan hendrerit nisi, sit amet venenatis dolor molestie quis. Proin purus magna, tempus quis 
                tempor vitae, scelerisque eu nisl. Suspendisse eros lacus, consequat id egestas a, rhoncus non leo. 
                Maecenas a ante a ante laoreet volutpat vitae ac augue.
                
                Cras at enim vulputate turpis vestibulum pellentesque sed at augue. Fusce eu eleifend nulla. Donec eget 
                malesuada ex. Fusce sit amet venenatis eros, quis scelerisque lorem. Morbi convallis magna id justo 
                lobortis, quis placerat lacus lacinia. Aliquam euismod tincidunt quam, vel sodales massa fringilla ut. 
                Duis in aliquet nibh. Curabitur in diam sit amet nunc fringilla pellentesque eget hendrerit nibh. Sed 
                sodales efficitur tincidunt. Vivamus malesuada mi eget nibh pellentesque mi.""");
        when(this.itemService.createItem(itemDTO)).thenReturn(this.createItemResponse());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(itemDTO));

        // When
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        // Then
        assertEquals(400, result.getResponse().getStatus());
        assertEquals("Invalid request content.", result.getResponse().getErrorMessage());
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

    @Test
    @DisplayName("Item is returned when requested")
    void getItem_whenItemIdRequested_shouldReturnItem() throws Exception {
        // Given
        when(this.itemService.findItemById(1L)).thenReturn(this.createItemResponse());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/items/1")
                .accept(MediaType.APPLICATION_JSON);

        // When
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        ItemResponse response = new ObjectMapper()
                .readValue(
                        result.getResponse().getContentAsString(),
                        new TypeReference<>() {}
                );

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("This is an Item test object", response.getDescription());
    }

    @Test
    @DisplayName("Get item not found triggers ItemNotFoundException")
    void getItem_whenGetItem_shouldReturnItemNotFoundException() throws Exception {
        // Given
        when(this.itemService.findItemById(99L)).thenThrow(new ItemNotFoundException(99L));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/items/99")
                .accept(MediaType.APPLICATION_JSON);

        // When
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        Map<String, Object> response = new ObjectMapper()
                .readValue(
                        result.getResponse().getContentAsString(),
                        new TypeReference<>() {}
                );

        // Then
        assertNotNull(response);
        assertEquals(404, response.get("status"));
        assertEquals("Not Found", response.get("error"));
        assertEquals("Item with id 99 not found", response.get("message"));
    }


    private ItemDTO createItemDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescription("This is a test item");
        return itemDTO;
    }

    private ItemResponse createItemResponse() {
        return new ItemResponse(1L, "This is an Item test object");
    }

    private List<ItemResponse> createItemResponses() {
        List<ItemResponse> itemResponses = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            itemResponses.add(new ItemResponse(i + 1, "Item response " + (i + 1)));
        }
        return itemResponses;
    }
}