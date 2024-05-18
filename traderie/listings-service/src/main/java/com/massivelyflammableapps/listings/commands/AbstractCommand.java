package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.service.ListingsService;
import com.massivelyflammableapps.shared.exceptions.UnauthorizedException;

import lombok.Data;

@Data
public abstract class AbstractCommand<T> /* implements Runnable */ {
    private ListingsService listingsService;

    public abstract T execute() throws UnauthorizedException;
}
