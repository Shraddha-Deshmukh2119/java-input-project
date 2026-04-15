package com.ecoomerce.sportscenter.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("BasketItem")
public class BasketItem {

    @Id
    private Integer id;

    private String name;
    private String description;
    private Double price;   // 🔥 changed (see below)
    private String pictureUrl;
    private String productBrand;
    private String productType;
    private Integer quantity;
}
