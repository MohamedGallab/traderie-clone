package com.massivelyflammableapps.listings.exceptions;

public class UnauthorizedException extends Exception{
    public UnauthorizedException() {
        super("Unauthorized access to this resource.");
    }
}
