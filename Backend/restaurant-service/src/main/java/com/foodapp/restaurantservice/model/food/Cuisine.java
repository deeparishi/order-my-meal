package com.foodapp.restaurantservice.model.food;

import com.foodapp.restaurantservice.enums.Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "cuisines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuisine {

    @Id
    private String id;

    @Indexed
    private String restaurantId;

    private String name;
    private String description;
    private String categoryId;
    private List<String> tags;
    private Enum.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}