package com.example.demo.services;

import com.example.demo.dao.CartRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;
    private CartRepository cartRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info

        Cart cart = purchase.getCart();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // populate cart with cartItems
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> cart.add(item));

        //populate cart with cart item and customer
        cart.setCartItem(purchase.getCartItems());
        cart.setCustomer(purchase.getCustomer());

        // populate customer with cart
        Customer customer = purchase.getCustomer();
        customer.add(cart);

        //set cart status to "ordered"
        cart.setStatus(StatusType.ORDERED);

        // save to the database
        customerRepository.save(customer);
        cartRepository.save(cart);

        // cart null validation
        if (cartItems.isEmpty()) {
            return new PurchaseResponse("Cart is empty");
        }

        //return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number
        return UUID.randomUUID().toString();

    }
}
