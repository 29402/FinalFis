package com.music.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT r FROM User r WHERE r.email = ?1")
    User findByEmail(String email);
}
