package com.webtools.huskyanswerswt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webtools.huskyanswerswt.dao.UserDAO;
import com.webtools.huskyanswerswt.exception.UserException;
import com.webtools.huskyanswerswt.pojo.User;

@RestController
public class UserController {
	
	
	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	@Autowired
	UserDAO userdao;
	
	@CrossOrigin(origins="http://localhost:4200")
    @RequestMapping(value= "/user/create", method= RequestMethod.POST)
    public User createUser(@RequestBody User user) throws UserException {
		user.setPassword(bcrypt.encode(user.getPassword()));
	    return userdao.register(user);
    }
	
	@CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/user/login", method= RequestMethod.POST)
    public User findUser(@RequestBody User user) throws UserException {
		User found = userdao.findUser(user);
		boolean userMatches = bcrypt.matches(user.getPassword(), found.getPassword());
		if(userMatches)
			return found;
		else
			return null;
    }
	
	@CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/users", method= RequestMethod.GET)
    public List<User> getAllUsers() throws UserException {
        return  userdao.getAllUsers();
    }

}
