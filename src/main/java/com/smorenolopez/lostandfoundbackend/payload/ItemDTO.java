package com.smorenolopez.lostandfoundbackend.payload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    @NotEmpty(message = "Description can not be empty")
    @Size(max = 2000, message = "Description can not exceed 2000 characters")
    private String description;
}
