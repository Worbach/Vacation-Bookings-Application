package com.example.demo.services;

import com.example.demo.dao.*;
import com.example.demo.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;
    private CartRepository cartRepository;
    private ExcursionRepository excursionRepository;
    private CartItemRepository cartItemRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository,
                               ExcursionRepository excursionRepository, CartItemRepository cartItemRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.excursionRepository = excursionRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        try {
            // Validate order data before proceeding
            Customer customer = purchase.getCustomer();
            Set<CartItem> cartItems = purchase.getCartItems();

            if (customer == null || cartItems == null || cartItems.isEmpty()) {
                throw new IllegalArgumentException("Customer can't be null and cart items can't be empty.");
            }

            // Retrieve cart and generate tracking number
            Cart cart = purchase.getCart();
            String orderTrackingNumber = generateOrderTrackingNumber();
            cart.setOrderTrackingNumber(orderTrackingNumber);

            // Associate cart items with cart
            cartItems.forEach(item -> item.setCart(cart));
            cart.setCartItems(cartItems);

            // Associate cart with customer
            cart.setCustomer(customer);
//            customer.add(cart);

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
