package com.epam.training.products;

import com.epam.training.products.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class ProductsReader {

    private File file;

    public ProductsReader(File file) {
        this.file = file;
    }

    public List<Product> read() {
        List<Product> products = List.of();
        try {

            ObjectMapper mapper = new ObjectMapper();
            products = List.of(mapper.readValue(file, Product[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
