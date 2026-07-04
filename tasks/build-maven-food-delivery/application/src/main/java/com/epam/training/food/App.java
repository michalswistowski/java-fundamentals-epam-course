package com.epam.training.food;

import com.epam.training.food.data.DataStore;
import com.epam.training.food.data.FileDataStore;
import com.epam.training.food.domain.Credentials;
import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;
import com.epam.training.food.service.DefaultFoodDeliveryService;
import com.epam.training.food.service.FoodDeliveryService;
import com.epam.training.food.values.FoodSelection;
import com.epam.training.food.view.CLIView;
import com.epam.training.food.view.View;

import java.util.List;

public class App {

    private final View view;
    private final DataStore dataStore;
    private final FoodDeliveryService foodDeliveryService;

    public App() {
        this.view = new CLIView();
        this.dataStore = new FileDataStore("test");
        this.foodDeliveryService = new DefaultFoodDeliveryService(dataStore);
    }

    private void run() {
        Customer customer = authenticateUser();
        view.printWelcomeMessage(customer);
        showMenu();

        while(true) {
            try {
                FoodSelection selection = handleCartUpdate(customer);
                view.printAddedToCart(selection.food(), selection.amount());
            } catch (Exception e) {
                checkout(customer);
                break;
            }
        }
    }

    private Customer authenticateUser() {
        Credentials credentials = view.readCredentials();
        return foodDeliveryService.authenticate(credentials);
    }

    private void showMenu() {
        view.printAllFoods(dataStore.getFoods());
    }

    private FoodSelection handleCartUpdate(Customer customer) {
        FoodSelection selection = view.readFoodSelection(dataStore.getFoods());
        foodDeliveryService.updateCart(customer, selection.food(), selection.amount());
        return selection;
    }

    private void checkout(Customer customer) {
        Order order = foodDeliveryService.createOrder(customer);
        view.printOrderCreatedStatement(order, customer.getBalance());
    }
}
