package com.lorenzo.springwebfluxrest.bootstrap;

import com.lorenzo.springwebfluxrest.domain.Category;
import com.lorenzo.springwebfluxrest.domain.Vendor;
import com.lorenzo.springwebfluxrest.repositories.CategoryRepository;
import com.lorenzo.springwebfluxrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0) {
            loadCategories();
            log.debug("Categories loaded");
        }
        if (vendorRepository.count().block() == 0) {
            loadVendors();
            log.debug("Vendors loaded");
        }

        log.debug("Vendors: " + vendorRepository.count().block());
        log.debug("Categories: " + categoryRepository.count().block());
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setFirstName("Agustin");
        vendor1.setLastName("Lopez");

        Vendor vendor2 = new Vendor();
        vendor2.setFirstName("Matias");
        vendor2.setLastName("Gutierrez");

        Vendor vendor3 = new Vendor();
        vendor3.setFirstName("Lucas");
        vendor3.setLastName("Martinez");

        Vendor vendor4 = new Vendor();
        vendor4.setFirstName("Pedro");
        vendor4.setLastName("Fernandez");

        vendorRepository.save(vendor1).block();
        vendorRepository.save(vendor2).block();
        vendorRepository.save(vendor3).block();
        vendorRepository.save(vendor4).block();
    }

    private void loadCategories() {
        Category category1 = new Category();
        category1.setDescription("Fruits");

        Category category2 = new Category();
        category2.setDescription("Meat");

        Category category3 = new Category();
        category3.setDescription("Snacks");

        Category category4 = new Category();
        category4.setDescription("Pasta");

        categoryRepository.save(category1).block();
        categoryRepository.save(category2).block();
        categoryRepository.save(category3).block();
        categoryRepository.save(category4).block();
    }

}
