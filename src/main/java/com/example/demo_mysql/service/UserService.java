package com.example.demo_mysql.service;

import com.example.demo_mysql.dao.Usermapping;
import com.example.demo_mysql.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Usermapping usermapping;

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, Usermapping usermapping) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usermapping = usermapping;
    }

  public void save(String username, String password){usermapping.save(username,bCryptPasswordEncoder.encode(password));}

  public User getUserByUsername(String username){
        return usermapping.findUserByUsername(username);
  }

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        User user =usermapping.findUserByUsername(username);
        if (user == null){
            throw  new BadCredentialsException(username+"不存在！");
        }
        return new org.springframework.security.core.userdetails.User(username,user.getEncryptedPassword(),Collections.emptyList());

    }
}
