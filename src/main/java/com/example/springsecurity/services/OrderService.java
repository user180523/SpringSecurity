package com.example.springsecurity.services;

import com.example.springsecurity.models.Order;
import com.example.springsecurity.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    public final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderId(int id){
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

//    @Transactional
//    public void updateOrder(int id, int status) {
////        order.setId(id);
//        orderRepository.updateOrderStatus(id,status);
//    }

    @Transactional
    public void updateOrder(int id, int status) {
//        order.setId(id);
        orderRepository.updateOrderStatus(id,status);
    }
}
