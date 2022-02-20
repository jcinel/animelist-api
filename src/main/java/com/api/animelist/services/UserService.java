package com.api.animelist.services;

import com.api.animelist.models.UserModel;
import com.api.animelist.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<UserModel> findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }
}
