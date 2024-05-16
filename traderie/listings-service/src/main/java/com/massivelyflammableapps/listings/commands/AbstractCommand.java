package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.service.ListingsService;
import lombok.Data;

@Data
public abstract class AbstractCommand<T> implements Runnable{
    private ListingsService listingsService;
    public abstract T execute() throws UnauthorizedException;
}
