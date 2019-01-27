package com.nmuzychuk.store.user;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.core.userdetails.User.withUsername;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user != null) {
            UserBuilder userBuilder = withUsername(email);
            userBuilder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            userBuilder.roles("ADMIN");
            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException("User " + email + " not found.");
        }
    }

    private User findByEmail(String email) {
        User user = null;
        if (email.equalsIgnoreCase("admin@admin")) {
            user = new User();
            user.setEmail("admin@admin");
            user.setPassword("admin");
        }
        return user;
    }
}
