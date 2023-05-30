package com.example.springsecurity.repositories;

import com.example.springsecurity.models.Order;
import com.example.springsecurity.models.Person;
import org.hibernate.type.descriptor.jdbc.SmallIntJdbcType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByPerson(Person person);

    @Query(value = "select * from orders", nativeQuery = true)
    List<Order> findOrders();

  //  List<Order> findById(int id);

    @Transactional
    @Query(value = "update orders set status = order_status where id = order_id", nativeQuery =
            true)
    List<Order> updateOrderStatus(int order_id, int order_status);
}


