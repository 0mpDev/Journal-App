 package com._mp.restAPI.controller;

import com._mp.restAPI.entity.User;
import com._mp.restAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 @RestController
 @RequestMapping("/user")
 public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public List<User> getAllUsers(){
   return userService.getAll();
  }

  @PostMapping
  public void createUser(@RequestBody User user) {
         userService.saveEntry(user);
  }

  @PutMapping("/{userName}")
  public ResponseEntity<?> updateUser(@RequestBody User user){
   User userInDb = userService.findByUserName(user.getUserName());
   if(userInDb != null){
    userInDb.setUserName(user.getUserName());
    userInDb.setPassword(user.getPassword());
    userService.saveEntry(userInDb);
   }
   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

 }
