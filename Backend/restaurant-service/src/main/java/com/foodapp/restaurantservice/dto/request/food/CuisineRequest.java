package com.foodapp.restaurantservice.dto.request.food;

import com.foodapp.restaurantservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuisineRequest {

    private String name;
    private String description;
    private List<String> tags;
    private Enum.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
