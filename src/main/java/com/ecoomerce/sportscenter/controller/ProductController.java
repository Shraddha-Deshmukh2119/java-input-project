package com.ecoomerce.sportscenter.controller;

import com.ecoomerce.sportscenter.model.BrandResponse;
import com.ecoomerce.sportscenter.model.ProductResponse;
import com.ecoomerce.sportscenter.model.TypeResponse;
import com.ecoomerce.sportscenter.service.BrandService;
import com.ecoomerce.sportscenter.service.ProductService;
import com.ecoomerce.sportscenter.service.TypeService;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final TypeService typeService;
    private final BrandService brandService;

    public ProductController(ProductService productService,
                             TypeService typeService,
                             BrandService brandService) {
        this.productService = productService;
        this.typeService = typeService;
        this.brandService = brandService;
    }

    // 🔹 Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        return ResponseEntity.ok(productResponse);
    }

    // 🔹 Get all products with filters + pagination
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "brandId", required = false) Integer brandId,
            @RequestParam(name = "typeId", required = false) Integer typeId,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {

        Page<ProductResponse> productResponsePage;

        if (brandId != null && typeId != null && keyword != null && !keyword.isEmpty()) {

            List<ProductResponse> list =
                    productService.searchProductsByBrandTypeAndName(brandId, typeId, keyword);

            productResponsePage = new PageImpl<>(list, pageable, list.size());

        } else if (brandId != null && typeId != null) {

            List<ProductResponse> list =
                    productService.searchProductsByBrandandType(brandId, typeId);

            productResponsePage = new PageImpl<>(list, pageable, list.size());

        } else if (brandId != null) {

            List<ProductResponse> list =
                    productService.searchProductsByBrand(brandId);

            productResponsePage = new PageImpl<>(list, pageable, list.size());

        } else if (typeId != null) {

            List<ProductResponse> list =
                    productService.searchProductsByType(typeId);

            productResponsePage = new PageImpl<>(list, pageable, list.size());

        } else if (keyword != null && !keyword.isEmpty()) {

            List<ProductResponse> list =
                    productService.searchProductsByName(keyword);

            productResponsePage = new PageImpl<>(list, pageable, list.size());

        } else {

            Sort.Direction direction =
                    "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;

            Sort sorting = Sort.by(direction, sort);

            productResponsePage = productService.getProducts(
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting)
            );
        }

        return ResponseEntity.ok(productResponsePage);
    }

    // 🔹 Get all brands
    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    // 🔹 Get all types
    @GetMapping("/types")
    public ResponseEntity<List<TypeResponse>> getTypes() {
        return ResponseEntity.ok(typeService.getAllTypes());
    }

    // 🔹 Search by keyword
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam("keyword") String keyword) {

        return ResponseEntity.ok(productService.searchProductsByName(keyword));
    }
}
