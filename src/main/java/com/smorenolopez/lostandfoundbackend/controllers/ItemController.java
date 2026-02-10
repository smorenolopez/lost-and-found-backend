package com.smorenolopez.lostandfoundbackend.controllers;

import com.smorenolopez.lostandfoundbackend.payload.ItemDTO;
import com.smorenolopez.lostandfoundbackend.payload.ItemResponse;
import com.smorenolopez.lostandfoundbackend.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemDTO itemDTO) {
        return new ResponseEntity<>(this.itemService.createItem(itemDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getItems() {
        return new ResponseEntity<>(this.itemService.findAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long id) {
        return new ResponseEntity<>(this.itemService.findItemById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long id,
                                                   @RequestBody @Valid ItemDTO itemDTO) {
        return new ResponseEntity<>(this.itemService.updateItem(id, itemDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemResponse> deleteItem(@PathVariable Long id) {
        return new ResponseEntity<>(this.itemService.deleteItem(id), HttpStatus.OK);
    }

}
