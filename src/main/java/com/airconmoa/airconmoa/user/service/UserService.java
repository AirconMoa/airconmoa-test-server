package com.airconmoa.airconmoa.user.service;

import com.airconmoa.airconmoa.user.repository.UserRepository;
import com.airconmoa.airconmoa.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
