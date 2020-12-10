package org.tms.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tms.finalproject.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
