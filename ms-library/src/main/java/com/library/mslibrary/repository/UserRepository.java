package com.library.mslibrary.repository;

import com.library.mslibrary.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    List<User> findAll();
    User findUserById (Long id);
    User findUserByEmail (String email);
    User findUserByLastName (String lastName);

}
