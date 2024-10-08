package kz.runtime.shop.repositories;

import kz.runtime.shop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByIdAsc();
    boolean existsByName(String nameCategory);
}