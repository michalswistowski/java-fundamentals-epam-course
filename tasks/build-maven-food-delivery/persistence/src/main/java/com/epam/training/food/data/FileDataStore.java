package com.epam.training.food.data;

import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class FileDataStore implements DataStore {

    private final String path;

    private List<Customer> customers;
    private List<Food> foods;
    private final List<Order> orders = new ArrayList<>();

    private final Reader<Customer> customerReader;
    private final Reader<Food> foodReader;
    private final OrderWriter orderWriter;

    public FileDataStore(String inputFolderPath) {
        this.customerReader = new CustomerReader();
        this.foodReader = new FoodReader();
        this.orderWriter = new OrderWriter();

        this.path = inputFolderPath;

    }

    public void init() {
        this.customers = customerReader.read(path + "/customers.csv");
        this.foods = foodReader.read(path + "/foods.csv");
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public List<Food> getFoods() {
        return foods;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order createOrder(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public void writeOrders() {
        orderWriter.writeOrders(orders, path + "/orders.csv");
    }
}
