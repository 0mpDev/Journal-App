package com._mp.restAPI.service;

import com._mp.restAPI.entity.User;
import com._mp.restAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceIMpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = userRepository.findByUserName(username);
          if(user != null){
             UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().
                      username(user.getUserName())
                      .password(user.getPassword())
                      .roles(user.getRoles().toArray(new String[0]))
                      .build();
              return userDetails;
          }
          throw new UsernameNotFoundException("User not found with username" + username);
    }
}
