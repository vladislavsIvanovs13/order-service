package org.order.services;

import org.order.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class OrderService {
    @Value("${cache.dataObjects}")
    private int dataObjects;

    @Value("${cache.probability}")
    private double probability;

    @Value("${cache.operations}")
    private int operations;

    public List<ProductDto> getProducts() {
        // retrieve all ids from product-service
        // localhost:8080/products/getIds
        // and save in data list

        List<Integer> flow = new ArrayList<>();
        Random random = new Random();
        List<ProductDto> products = new ArrayList<>();

        // need to check modelling course technic about probabilities and ifs
        for (int i = 0; i < operations; i++) {
            int bound = (int) Math.round(0.1 * dataObjects);
            if (random.nextDouble() < probability)
                flow.add(data.get(random.nextInt(0, bound)));
            else
                flow.add(data.get(random.nextInt(bound, dataObjects)));
        }

        var end = System.nanoTime();
        for (int productId : flow)
            products.add(getProduct(productId));

        // many calls to
        // localhost:8080/products/{productId}
        // have to be made

        System.out.println("Serving " + dataObjects + " products");
        System.out.printf(Locale.US, "Execution time: %.4f s%n", (System.nanoTime() - end) / 1_000_000_000.0);
        return products;
    }
}
