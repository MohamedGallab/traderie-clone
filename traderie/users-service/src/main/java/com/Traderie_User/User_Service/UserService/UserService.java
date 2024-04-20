package com.Traderie_User.User_Service.UserService;
import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.UserRegistery.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private void registerUser(User user) {
//        userRepository.save(user);
    }
}
