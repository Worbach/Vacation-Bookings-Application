package com.example.demo.bootstrap;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Setting divisions for each customer
        Division holmesDiv = divisionRepository.findById(Long.valueOf(4)).orElse(null);
        Division addamsDiv = divisionRepository.findById(Long.valueOf(3)).orElse(null);
        Division summersDiv = divisionRepository.findById(Long.valueOf(5)).orElse(null);
        Division wayneDiv = divisionRepository.findById(Long.valueOf(9)).orElse(null);
        Division flintstoneDiv = divisionRepository.findById(Long.valueOf(101)).orElse(null);

        // Checking if sample customers already exist inside the database
        if (customerRepository.count() == 1) {

            // Creating new customers - one to five
            Customer holmes = new Customer("Sherlock", "Holmes", "221B Baker Street", "34711", "(915)877-9185", holmesDiv);
            Customer addams = new Customer("Morticia", "Addams", "0001 Cemetery Lane", "06074", "(718)549-3049", addamsDiv);
            Customer summers = new Customer("Buffy", "Summers", "518 Crestview Drive", "90210", "(206)342-8631", summersDiv);
            Customer wayne = new Customer("Bruce", "Wayne", "1 Wayne Manor", "75043", "(253)644-1822", wayneDiv);
            Customer flintstone = new Customer("Fred", "Flintstone", "301 Cobblestone Way", "43420", "(201)874-8593", flintstoneDiv);

            // Saving each customer to the customer repository
            customerRepository.save(holmes);
            customerRepository.save(addams);
            customerRepository.save(summers);
            customerRepository.save(wayne);
            customerRepository.save(flintstone);

            System.out.println("Sample customers added.");

        } else {
            System.out.println("Sample customers exist already!");
            // TODO is this needed?
            customerRepository.findAll();
        }

    }
}
