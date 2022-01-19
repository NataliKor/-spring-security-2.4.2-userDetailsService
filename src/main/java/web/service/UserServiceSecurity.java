package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.model.User;

@Service
public class UserServiceSecurity implements UserDetailsService {
    @Autowired
    private UserService userService;

//    @Autowired
//    public UserServiceSecurity(UserServiceImpl userService) {
//        this.userService = userService;
//    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.searchForUserByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
