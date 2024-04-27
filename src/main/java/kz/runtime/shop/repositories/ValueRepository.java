package kz.runtime.shop.repositories;

import kz.runtime.shop.models.Option;
import kz.runtime.shop.models.Product;
import kz.runtime.shop.models.Value;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {

    List<Value> findAllByProduct(Product product);
}
