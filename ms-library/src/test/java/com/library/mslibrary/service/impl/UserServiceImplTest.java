package com.library.mslibrary.service.impl;

import com.library.mslibrary.entities.User;
import com.library.mslibrary.enumerated.UserRoleEnum;
import com.library.mslibrary.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.library.mslibrary.mock.UserMock.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void findAll(){
        List<User> userList = getMockUserList();

        given(userRepository.findAll()).willReturn(userList);

        Assertions.assertEquals(
                userList.size(),
                userService.findAll().size(),
                "Liste des utilisateurs"
        );
    }

    @Test
    void findUserById(){
        User user = getMockUser();
        long userId = 1;
        user.setId(userId);
        given(userRepository.findUserById(anyLong())).willReturn(user);

        Assertions.assertEquals(
                userId,
                userService.findUserById(userId).getId(),
                "Recherche d'utilisateur par userId"
        );
    }

    @Test
    void findUserByEmail(){
        User user = getMockUser();
        String email = "email@fai.com";
        user.setEmail(email);
        given(userRepository.findUserByEmail(anyString())).willReturn(user);

        Assertions.assertEquals(
                email,
                userService.findUserByEmail(email).getEmail(),
                "Recherche d'utilisateur par eMail"
        );
    }

    @Test
    void saveUser(){
        User user = getMockUser();

        given(userRepository.save(any(User.class))).willReturn(user);

        Assertions.assertEquals(
                user.getId(),
                userService.saveUser(user).getId(),
                "Enregistrement d'un utilisateur"
        );
    }

    @Test
    void saveAll(){
        List<User> userList = getMockUserList();

        given(userRepository.saveAll(anyIterable())).willReturn(userList);

        Assertions.assertEquals(
                userList.size(),
                userService.saveAll(userList).size(),
                "Enregistrement d'une liste d'utilisateurs'"
        );
    }

    @Test
    void getRoleList(){
        Assertions.assertEquals(
                UserRoleEnum.values().length,
                userService.getRoleList().size(),
                "Liste des rôles utilisateurs"
        );
    }
}
