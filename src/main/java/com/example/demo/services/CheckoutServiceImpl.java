package com.example.demo.services;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService{
    private final CustomerRepository customerRepository;

    private final CartRepository cartRepository;

    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        try {
            // Validate order data before proceeding
            Customer customer = purchase.getCustomer();
            Set<CartItem> cartItems = purchase.getCartItems();

            if (customer == null || cartItems == null || cartItems.isEmpty()) {
                throw new IllegalArgumentException("Customer can't be null and cart items can't be empty.");
            }

            Cart cart = purchase.getCart();

            cart.setId(null);
            String orderTrackingNumber = generateOrderTrackingNumber();
            cart.setOrderTrackingNumber(orderTrackingNumber);

            // Associate cart items with cart
            cartItems.forEach(item -> item.setCart(cart));
            cart.setCartItems(cartItems);

            // Associate cart with customer
            cart.setCustomer(customer);

            // Set cart status to 'ordered'
            cart.setStatus(StatusType.ordered);

            // Save to the database
            customerRepository.save(customer);
            cartRepository.save(cart);

            // Return response
            return new PurchaseResponse(orderTrackingNumber);

        } catch (Exception e) {
            throw new RuntimeException("Error processing order: " + e.getMessage(), e);
        }
    }
    private String generateOrderTrackingNumber() {
        // generate a random UUID number
        return UUID.randomUUID().toString();
    }
}
