package com.Traderie_User.User_Service.UserService;

import com.Traderie_User.User_Service.Configuration.RedisConfiguration;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BlackListingService {

    @CachePut(RedisConfiguration.BLACKLIST_CACHE_NAME)
    public String blackListJwt(String jwt) {
        return jwt;
    }

    @Cacheable(value = RedisConfiguration.BLACKLIST_CACHE_NAME, unless = "#result == null")
    public String getJwtBlackList(String jwt) {
        return null;
    }

}