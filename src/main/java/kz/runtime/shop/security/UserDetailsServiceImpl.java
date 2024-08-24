package kz.runtime.shop.security;

import kz.runtime.shop.models.User;
import kz.runtime.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        if(user==null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return userDetails;
    }
}