package com.massivelyflammableapps.offers.commands;

import com.massivelyflammableapps.offers.service.OffersService;

import lombok.Data;

@Data
public abstract class AbstractCommand<T> {
    private OffersService offersService;
    public abstract T execute();
}
