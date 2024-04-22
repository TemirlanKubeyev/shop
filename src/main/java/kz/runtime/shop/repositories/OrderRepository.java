package kz.runtime.shop.repositories;

import kz.runtime.shop.models.Order;
import kz.runtime.shop.models.User;
import kz.runtime.shop.models.enumeration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatusOrderByIdAsc(OrderStatus orderStatus);

    List<Order> findAllByOrderByIdAsc();

    List<Order> findAllByUserOrderByIdAsc(User user);

}
