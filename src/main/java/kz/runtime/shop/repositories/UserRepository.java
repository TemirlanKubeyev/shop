package kz.runtime.shop.repositories;

import kz.runtime.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);
}
