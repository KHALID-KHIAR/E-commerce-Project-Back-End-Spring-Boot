package com.kh.EcomercebackEndspringboot.Repository;

import com.kh.EcomercebackEndspringboot.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    public Optional<User> findByEmail(String email);
}
