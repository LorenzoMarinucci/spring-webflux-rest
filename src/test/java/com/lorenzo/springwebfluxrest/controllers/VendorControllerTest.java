package com.lorenzo.springwebfluxrest.controllers;

import com.lorenzo.springwebfluxrest.domain.Vendor;
import com.lorenzo.springwebfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class VendorControllerTest {

    final String ID = "1";

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void listVendors() {
        Vendor vendor = new Vendor();
        vendor.setFirstName("Carlos");
        vendor.setLastName("Martinez");
        Vendor vendor2 = new Vendor();
        vendor2.setFirstName("Lucas");
        vendor2.setLastName("Gonzalez");

        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(vendor, vendor2));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setFirstName("Carlos");
        vendor.setLastName("Martinez");
        vendor.setId(ID);

        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(vendor));

        webTestClient.get().uri("/api/v1/vendors/" + ID)
                .exchange()
                .expectBody(Vendor.class);
    }
}