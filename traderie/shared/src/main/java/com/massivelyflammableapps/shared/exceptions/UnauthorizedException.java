package com.massivelyflammableapps.shared.exceptions;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super("Unauthorized access to this resource.");
    }
}
