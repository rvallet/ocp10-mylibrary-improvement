package com.library.mslibrary.mock;

import com.library.mslibrary.entities.User;
import com.library.mslibrary.enumerated.UserRoleEnum;

import java.util.Arrays;
import java.util.List;

public class UserMock {

    private UserMock(){};

    public static User getMockUser(){
        User user = new User(
                "user1@email.com",
                "lastName",
                "firstName",
                "password",
                UserRoleEnum.USER.toString()
        );
        user.setId(0L);
        return user;
    }

    public static List<User> getMockUserList(){
        return Arrays.asList(getMockUser(), getMockUser());
    }

    public static User getMockUserAdmin(){
        User user = new User(
                "user1@email.com",
                "lastName",
                "firstName",
                "password",
                UserRoleEnum.ADMIN.toString()
        );
        user.setId(0L);
        return user;
    }
}
