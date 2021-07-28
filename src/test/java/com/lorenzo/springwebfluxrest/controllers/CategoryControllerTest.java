package com.lorenzo.springwebfluxrest.controllers;

import com.lorenzo.springwebfluxrest.domain.Category;
import com.lorenzo.springwebfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;


class CategoryControllerTest {

    private final String ID = "1";

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        Category category = new Category();
        category.setDescription("Fruits");
        Category category2 = new Category();
        category2.setDescription("Meat");
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(category, category2));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        Category category = new Category();
        category.setDescription("Fruits");
        category.setId(ID);
        BDDMockito.given(categoryRepository.findById(ID))
                .willReturn(Mono.just(category));

        webTestClient.get().uri("/api/v1/categories/" + ID)
                .exchange()
                .expectBody(Category.class);
    }
}