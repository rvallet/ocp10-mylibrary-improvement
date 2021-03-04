package com.library.mslibrary.service.impl;

import com.library.mslibrary.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    void findAll(){

    }

    void findUserById(){

    }

    void findUserByEmail(){

    }

    void saveUser(){

    }

    void saveAll(){

    }

    void getRoleList(){
        
    }
}
