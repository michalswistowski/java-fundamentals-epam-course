package com.epam.training.food.service;

import com.epam.training.food.data.DataStore;
import com.epam.training.food.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultFoodDeliveryService implements FoodDeliveryService {

    private final DataStore store;

    public DefaultFoodDeliveryService(DataStore dataStore) {
        this.store = dataStore;
    }

    @Override
    public Customer authenticate(Credentials credentials) throws AuthenticationException {

        return store.getCustomers().stream().filter(
                customer -> customer.getUserName().equals(credentials.getUserName()) && customer.getPassword().equals(credentials.getPassword()))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("Authentication failed"));
    }

    @Override
    public List<Food> listAllFood() {
        return store.getFoods();
    }

    @Override
    public void updateCart(Customer customer, Food food, int pieces) throws LowBalanceException {

        validateInputs(customer, food, pieces);
        Cart cart = customer.getCart();

        List<OrderItem> foundItems = cart.getOrderItems().stream()
                .filter(item -> item.getFood().equals(food))
                .toList();

        if (pieces == 0) {
            removeItemsFromCart(customer, food);
            return;
        }


        if (!foundItems.isEmpty()) {
            updateExistingItems(customer, food, pieces);
            return;
        } else {
            addNewItemWithBalanceCheck(customer, food, pieces);
        }
    }

    private void validateInputs(Customer customer, Food food, int pieces) {
        if (pieces == 0 && customer.getCart().getOrderItems().stream().noneMatch(item -> item.getFood().equals(food))) {
            throw new IllegalArgumentException("Cannot remove %s from cart: not present".formatted(food.getName()));
        }

        if (pieces < 0) {
            throw new IllegalArgumentException("Pieces must be >= 0");
        }
    }

    private void updateExistingItems(Customer customer, Food food, int pieces) {

        Cart cart = customer.getCart();
        List<OrderItem> items = cart.getOrderItems();
        BigDecimal currPrice = cart.getPrice();

        items.replaceAll(item -> {

            BigDecimal currItemPrice = item.getPrice();
            BigDecimal newItemPrice = item.getFood().getPrice().multiply(BigDecimal.valueOf(pieces));

            int compareResult = currPrice.subtract(currItemPrice).add(newItemPrice).compareTo(customer.getBalance());
            if (compareResult < 0 && item.getFood().equals(food)) {
                item.setPieces(pieces);
                item.setPrice(item.getFood().getPrice().multiply(BigDecimal.valueOf(pieces)));
            }
            return item;
        });

        cart.setPrice(calculatePrice(items));
    }

    private void removeItemsFromCart(Customer customer, Food food) {

        Cart cart = customer.getCart();
        List<OrderItem> items = cart.getOrderItems();

        List<OrderItem> foundItems = cart.getOrderItems().stream()
                .filter(item -> item.getFood().equals(food))
                .toList();

        items.removeAll(foundItems);

        BigDecimal newPrice = calculatePrice(items);

        cart.setOrderItems(items);
        cart.setPrice(newPrice);

        customer.setCart(cart);
    }

    private void addNewItemWithBalanceCheck(Customer customer, Food food, int pieces) {

        Cart cart = customer.getCart();
        BigDecimal currPrice = cart.getPrice();
        BigDecimal addedPrice = food.getPrice().multiply(BigDecimal.valueOf(pieces));
        List<OrderItem> items = cart.getOrderItems();

        if ((currPrice.add(addedPrice)).compareTo(customer.getBalance()) > -1) {
            throw new LowBalanceException("Balance is too low!");
        }

        OrderItem newItem = new OrderItem(food, pieces, addedPrice);
        items.add(newItem);

        cart.setOrderItems(items);
        cart.setPrice(currPrice.add(addedPrice));
    }

    private BigDecimal calculatePrice(List<OrderItem> items) {
        return items.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order createOrder(Customer customer) throws IllegalStateException {
        Cart cart = customer.getCart();

        if (cart == null || cart.getOrderItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order: cart is empty");
        }

        Order order = new Order((long) store.getOrders().size(),
                customer.getId(),
                cart.getOrderItems(),
                customer.getCart().getPrice(),
                LocalDateTime.now()
                );

        customer.setBalance(customer.getBalance().subtract(order.getPrice()));
        customer.getOrders().add(order);

        cart.setOrderItems(new ArrayList<>());
        cart.setPrice(BigDecimal.ZERO);
        return store.createOrder(order);
    }
}
