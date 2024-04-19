package com.massivelyflammableapps.offers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("api/v1/offers")
public class OffersController {

    @Autowired
    OffersRepository offersRepository;
 
    @GetMapping
    public String getMethodName(@RequestParam(required = false) String param) {
        return "yay you made it";
    }

    //get offers by listing id
    @GetMapping("/getByListing")
    public List<Offer> getEmployee(@RequestParam String listingId)
    {
        return offersRepository.findByListingId(listingId);
    }
}

