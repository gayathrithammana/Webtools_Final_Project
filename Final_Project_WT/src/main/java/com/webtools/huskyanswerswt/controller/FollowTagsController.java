package com.webtools.huskyanswerswt.controller;

import java.util.List;

//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.Email;
//import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webtools.huskyanswerswt.dao.FollowTagsDAO;
import com.webtools.huskyanswerswt.exception.FollowTagsException;
import com.webtools.huskyanswerswt.pojo.FollowedTag;

@RestController
public class FollowTagsController {
	
	@Autowired
	FollowTagsDAO followTagsDao;
	
	// Method to test the angular fetch call.
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/followedTags/{userId}", method= RequestMethod.GET) 
    public List<FollowedTag> getFollowedTagsByUserId(@PathVariable("userId") long userId) throws FollowTagsException{
    	return followTagsDao.getTagsByUserId(userId);
    }
    
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/follow", method= RequestMethod.POST) 
    public FollowedTag followTag(@RequestBody FollowedTag tag) throws FollowTagsException{
    	sendEmail(null, null);
    	return followTagsDao.followTag(tag);
    }
    
    public void sendEmail(String useremail, String message) {
//        try{
//		Email email = new SimpleEmail();
//		email.setHostName("smtp.googlemail.com");
//		email.setSmtpPort(465);
//		email.setAuthenticator(new DefaultAuthenticator("gayathri.thammana1837@gmail.com", "Gayathri@264"));
//		email.setSSLOnConnect(true);
//		
//	    email.setFrom("thammana.g@husky.neu.edu");
//	    email.setSubject("Password Reminder");
//	    email.setMsg("Hi");
//	    email.addTo("thammana.g@husky.neu.edu");
//	    email.send();
//        } catch(Exception e)
//	    {
//	      System.out.println("email was not sent" +e.getMessage());
//	    }
	}

}
