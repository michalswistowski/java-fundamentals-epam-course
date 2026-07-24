package com.epam.training.products;

import com.epam.training.products.domain.Product;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ProductQueries {

    private final List<Product> products;

    public ProductQueries(List<Product> products) {
        this.products = products;
    }

    public List<String> getSubCategoriesOf(String categoryName) {

        return products.stream()
                .filter(product ->
                                product.getCategories().length > 0 &&
                                product.getCategories()[0].equals(categoryName))
                .map(product -> Arrays.stream(product.getCategories()).toList())
                .flatMap(Collection::stream)
                .filter(category -> !category.equals(categoryName))
                .distinct()
                .toList();
    }

    public List<Product> getSweetsWherePriceIsLowerThan(double price) {
        return products.stream()
                .filter(product -> product.getCategories().length > 0 &&
                        product.getCategories()[0].equals("sweets") &&
                        product.getPrice() < price)
                .toList();
    }

    public Product getTheMostExpensiveProduct() {
        return products.stream().max(Comparator.comparing(Product::getPrice)).orElseThrow();
    }
}
