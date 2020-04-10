package com.example.demo_mysql.controller;

import com.example.demo_mysql.entity.Result;
import com.example.demo_mysql.entity.User;
import com.example.demo_mysql.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    public AuthController(UserService userService,AuthenticationManager authenticationManager){
        this.userService=userService;
        this.authenticationManager=authenticationManager;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        //从cookie中拿去用户之前登陆的信息
       String username= SecurityContextHolder.getContext().getAuthentication().getName();
       User loggedInUser=userService.getUserByUsername(username);
       if(loggedInUser == null){
           return Result.failure("未登录")   ;
       }else{
           return new Result("ok","登陆成功",true,loggedInUser);
       }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Object logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.getUserByUsername(username);
        if (loggedInUser == null) {
            return  Result.failure("用户尚未登陆");
        } else {
            SecurityContextHolder.clearContext();   //将登陆的信息清掉
            return new Result("ok","注销成功",false);
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        if (username == null || password == null) {
            return Result.failure("用户名或者密码为空");
        } else if (username.length() < 1 || username.length() > 15) {
            return Result.failure("用户名太短或者太长");
        } else if (password.length() < 1 || password.length() > 15) {
            return Result.failure("密码太短或者太长");
        }

        try {
            userService.save(username, password);   //这里注意将数据库中的username字段设置为unique
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return Result.failure("错误原因");  //返回失败的信息
        }
        return new Result("ok", "注册成功", false, userService.loadUserByUsername(username)); //返回注册成功的信息
        //       User user=userService.getUserByname(username);
//        if(user == null){
//            userService.save(username,password);
//            return new Result();
//        }else{
//            return new Result();
//        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> usernameAndPassword) {
        String userName =usernameAndPassword.get("username").toString();
        String passwd = usernameAndPassword.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(userName);
        } catch (BadCredentialsException e) {                         //这里需要注意BadCredentialsException异常会覆盖掉用户名不存在的异常
            return  Result.failure("用户不存在");  //返回用户不存在的信息
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, passwd);
        try {
            authenticationManager.authenticate(token);
            //把用户信息保存在一个地方 cookie
            SecurityContextHolder.getContext().setAuthentication(token);
            return new Result("ok","登陆成功",true,userService.getUserByUsername(userName)); //返回登陆成功的信息
        } catch (BadCredentialsException e) {
            return  Result.failure("密码不正确");  //返回登陆失败的信息
        }
    }

}
