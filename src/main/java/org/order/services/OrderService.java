package org.order.services;

import org.order.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OrderService {
    @Value("${cache.dataObjects}")
    private int dataObjects;

    @Value("${cache.probability}")
    private double probability;

    @Value("${cache.weight}")
    private double weight;

    @Value("${cache.operations}")
    private int operations;

    public List<ProductDto> getProducts() {
        RestTemplate restTemplate = new RestTemplate();
        List<Integer> data =
                restTemplate.exchange(
                    "http://localhost:8080/products/getIds",
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Integer>>() {})
                .getBody();

        List<Integer> flow = new ArrayList<>();
        Random random = new Random();
        List<ProductDto> products = new ArrayList<>();
        Collections.shuffle(data);

        for (int i = 0; i < operations; i++) {
            int bound = (int) Math.round(weight * dataObjects);
            if (random.nextDouble() < probability)
                flow.add(data.get(random.nextInt(0, bound)));
            else
                flow.add(data.get(random.nextInt(bound, dataObjects)));
        }

        var end = System.nanoTime();
        for (int productId : flow) {
            var product = restTemplate.getForObject(
                "http://localhost:8080/products/{productId}",
                ProductDto.class, productId);
            products.add(product);
        }

        System.out.println("Received " + dataObjects + " products");
        System.out.printf(Locale.US, "Execution time: %.4f s%n", (System.nanoTime() - end) / 1_000_000_000.0);
        return products;
    }
}
