package com.example.daetaserver.deleteUser;

import com.example.daetaserver.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteUserRepository extends JpaRepository<DeleteUser, User> {
    boolean existsByUser(User user);
}
