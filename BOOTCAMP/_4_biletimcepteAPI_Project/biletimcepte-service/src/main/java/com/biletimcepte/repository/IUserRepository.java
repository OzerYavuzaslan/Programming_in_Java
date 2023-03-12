package com.biletimcepte.repository;

import com.biletimcepte.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameIgnoreCase(String username);

    @Query(nativeQuery = true, value = "select * from users where email = :email order by id")
    Optional<User> selectByEmail(String email);

    @Query(nativeQuery = true, value = "select * from users order by id")
    List<User> getAllUsers();
}
