package com.kh.EcomercebackEndspringboot.Repository;

import com.kh.EcomercebackEndspringboot.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByEmail(String email);
}
