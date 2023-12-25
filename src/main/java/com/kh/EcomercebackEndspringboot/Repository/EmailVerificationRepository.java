package com.kh.EcomercebackEndspringboot.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kh.EcomercebackEndspringboot.Entity.EmailVerification;
public interface EmailVerificationRepository extends JpaRepository<EmailVerification,Integer> {
    public EmailVerification searchByEmail(String email);
    @Transactional
    public void deleteByEmail(String email);
}
