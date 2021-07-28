package com.lorenzo.springwebfluxrest.controllers;

import com.lorenzo.springwebfluxrest.domain.Category;
import com.lorenzo.springwebfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


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

    @Test
    void createCategory() {
        Category category = new Category();
        category.setDescription("Fruits");
        Flux<Category> flux = Flux.just(new Category());
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(flux);

        Mono<Category> catToSaveMono = Mono.just(category);

        webTestClient.post().uri("/api/v1/categories")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateCategory() {
        Category category = new Category();
        category.setDescription("Fruits");
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(new Category()));

        Mono<Category> catToUpdateMono = Mono.just(category);
        webTestClient.put().uri("/api/v1/categories/" + ID)
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

    }
}