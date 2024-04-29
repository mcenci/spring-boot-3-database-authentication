package com.cenci.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cenci.security.dao.entity.User;
import com.cenci.security.dao.repository.UserRepository;

@Profile(value = "development")
@Component
public class BulkAdminUser {

	private static final Logger LOGGER = LoggerFactory.getLogger(BulkAdminUser.class);
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BulkAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Init first user for login
     */
    public void initRootAdmin() {
        String email = "mcenci@intesigroup.com";
        userRepository.findByUsername(email).orElseGet(() -> {

            User user = new User();
            user.setId(100L);
            user.setUsername("mcenci@intesigroup.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setPhone("+39123123123");
            user.setRole("ADMIN");
            user.setEnabled(true);
            userRepository.save(user);

            LOGGER.info("Created root user: {}", user.getId());
            return user;
        });

    }
}