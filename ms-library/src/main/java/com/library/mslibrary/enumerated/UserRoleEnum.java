package com.library.mslibrary.enumerated;

public enum UserRoleEnum {

    USER ("user"),
    ADMIN ("admin");

    private String userRole;

    UserRoleEnum(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return userRole;
    }
}
