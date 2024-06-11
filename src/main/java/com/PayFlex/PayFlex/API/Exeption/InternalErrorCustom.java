package com.PayFlex.PayFlex.API.Exeption;

public class InternalErrorCustom extends RuntimeException{

    public InternalErrorCustom(String serverError) {
        super(serverError);
    }
}
