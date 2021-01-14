package com.library.mslibrary.service;

import com.library.mslibrary.entities.User;

import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findUserById (Long id);
    User findUserByEmail (String email);
    User saveUser(User user);
    List<User> saveAll(List<User> userList);
    Page<User> findPaginatedUsers(int pageNumber, int pageSize);
    List<String> getRoleList ();

}
