package org.order.controllers;

import lombok.AllArgsConstructor;
import org.order.dto.ProductDto;
import org.order.services.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {
    private OrderService orderService;

    @GetMapping("/get")
    public List<ProductDto> getProducts() {
        return orderService.getProducts();
    }
}
