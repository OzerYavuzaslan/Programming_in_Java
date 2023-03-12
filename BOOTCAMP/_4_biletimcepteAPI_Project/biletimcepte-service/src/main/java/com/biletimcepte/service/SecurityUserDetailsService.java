package com.biletimcepte.service;

import com.biletimcepte.model.User;
import com.biletimcepte.repository.IUserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.biletimcepte.util.Constants.USER_NOT_FOUND;

@Data
@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private IUserRepository iUserRepository;

    @Autowired
    public SecurityUserDetailsService(IUserRepository iUserRepository) {
       setIUserRepository(iUserRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getIUserRepository()
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    public void createUser(UserDetails user) {
        getIUserRepository().save((User) user);
    }
}