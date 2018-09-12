package com.tessi.kyc.user.dto;

import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Data
public class UserDto {

    private final String username;
    private final String password;
}
