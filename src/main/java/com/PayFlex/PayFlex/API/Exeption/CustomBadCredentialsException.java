package com.PayFlex.PayFlex.API.Exeption;

import org.springframework.security.authentication.BadCredentialsException;

public class CustomBadCredentialsException extends RuntimeException {
    public CustomBadCredentialsException(String msg) {
        super(msg);
    }
}
