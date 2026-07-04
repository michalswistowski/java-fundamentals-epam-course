package com.epam.training.food.view;

import com.epam.training.food.domain.Credentials;
import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;
import com.epam.training.food.values.FoodSelection;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CLIView implements View {

    Scanner scanner = new Scanner(System.in);

    @Override
    public Credentials readCredentials() {
        System.out.print("Enter customer name:");
        String username = scanner.nextLine();
        System.out.println("Enter customer password:");
        String password = scanner.nextLine();

        return new Credentials(username, password);
    }

    @Override
    public void printWelcomeMessage(Customer customer) {
        System.out.printf("Welcome, %s. Your balance is: %s EUR.%n", customer.getName(), customer.getBalance().toString());
    }

    @Override
    public void printAllFoods(List<Food> foods) {
        System.out.println("Foods offered today:");
        foods.forEach(food -> {
            System.out.printf("- %s %s EUR each", food.getName(), food.getPrice().toString());
        });
    }

    @Override
    public FoodSelection readFoodSelection(List<Food> foods) {
        System.out.print("Please enter the name and amount of food (separated by comma) you would like to buy:");
        String input = scanner.nextLine();
        Optional<Food> foundFood = foods.stream().filter(food -> food.getName().equals(input.split(",")[0])).findFirst();
        if (foundFood.isPresent()) {
            return new FoodSelection(foundFood.get(), Integer.parseInt(input.split(",")[1]));
        } else {
            throw new IllegalArgumentException("Food should be in menu!");
        }
    }

    @Override
    public void printAddedToCart(Food food, int pieces) {
        System.out.printf("Added %d piece(s) of %s to the cart.", pieces, food.getName());
    }

    @Override
    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printOrderCreatedStatement(Order order, BigDecimal balance) {
        String items = order.getOrderItems().stream().map(item -> item.getFood().getName()).collect(Collectors.joining(","));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("Order (items: [%s], price: %s EUR, timestamp: %s) has been confirmed.\n" +
                "Your balance is %s EUR.\n" +
                "Thank you for your purchase.", items, order.getPrice().toString(), order.getTimestampCreated().format(formatter), balance.toString());
    }
}
