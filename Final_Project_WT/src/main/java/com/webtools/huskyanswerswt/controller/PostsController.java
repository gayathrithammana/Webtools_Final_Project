package com.webtools.huskyanswerswt.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webtools.huskyanswerswt.dao.FollowTagsDAO;
import com.webtools.huskyanswerswt.dao.PostsDAO;
import com.webtools.huskyanswerswt.exception.FollowTagsException;
import com.webtools.huskyanswerswt.exception.PostException;
import com.webtools.huskyanswerswt.pojo.Post;
import com.webtools.huskyanswerswt.pojo.Tag;

@RestController
public class PostsController {
	@Autowired
	PostsDAO postDao;
	
	@Autowired
	FollowTagsDAO followTagsDao;
	
	// Method to test the angular fetch call.
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/posts", method= RequestMethod.GET) 
    public List<Post> getPosts() throws PostException {
        return postDao.getAllPosts();
    }
    
    // Method to test the angular fetch call.
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/posts/size", method= RequestMethod.GET) 
    public long getAllPostsSize() throws PostException {
        return postDao.getAllPostsSize();
    }
    
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/posts/userId={userId}/size", method= RequestMethod.GET) 
    public long getAllUserPostsSize(@PathVariable("userId") long userId) throws PostException {
        return postDao.getAllUserPostsSize(userId);
    }
    
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/posts/pn={pn}/limit={limit}", method= RequestMethod.GET) 
    public List<Post> getPostsPerPage(@PathVariable("pn") int pageNumber, @PathVariable("limit") int pageSize) throws PostException {
        return postDao.getPostsPerPage(pageNumber, pageSize);
    }
    
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/posts/userId={userId}/pn={pn}/limit={limit}", method= RequestMethod.GET) 
    public List<Post> getUserPostsPerPage(@PathVariable("userId") long userId, @PathVariable("pn") int pageNumber, @PathVariable("limit") int pageSize) throws PostException {
        return postDao.getUserPostsPerPage(userId, pageNumber, pageSize);
    }
    
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/posts/{postId}", method= RequestMethod.GET)
    @ResponseBody
    public Post getPostById(@PathVariable("postId") long id) throws PostException {
        return  postDao.findById(id);
    }
    
//    @CrossOrigin(origins="http://localhost:4200")
//    @RequestMapping(value= "/posts", method= RequestMethod.POST)
//    public Post createPost(@RequestBody Post post) throws PostException {
//        return postDao.createPost(post);
//    }
    
    @CrossOrigin(origins="http://localhost:4200")
    @RequestMapping(value= "/posts", method= RequestMethod.POST)
    public Post createPost(@RequestParam("title") String title,
    		@RequestParam("description") String desc,
    		@RequestParam("userId") long userId,
    		@RequestParam("tags") String tagString,
    		@RequestParam("fileContent") Optional<MultipartFile> fileContent
    		) throws PostException, IOException, FollowTagsException {
    	
    	System.out.println(tagString);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	Tag[] tagsArray = mapper.readValue(tagString, Tag[].class);
        Set<Tag> tagsSet = new HashSet<Tag>(Arrays.asList(tagsArray));
        
        List<String> tagsList = new ArrayList<String>();
        for(Tag tag: tagsArray) {
        	tagsList.add(tag.getName());
        }
    	
    	Post p = new Post();
    	p.setDescription(desc);
    	p.setFileContent(fileContent.isPresent() ? fileContent.get().getBytes() : null);
    	p.setTags(tagsSet);
    	p.setTitle(title);
    	p.setUserId(userId);
    	
    	List<String> emailList = followTagsDao.getUserIds(tagsList);
    	for(String s: emailList) {
    		System.out.println("user emails........"+s);
    		sendEmail(s, "There is a new post on the topic you have followed");
    	}
    	
    	
    	
        return postDao.createPost(p);
    }
    
    @CrossOrigin(origins="http://localhost:4200")
    @RequestMapping(value= "/posts", method= RequestMethod.PUT)
    public Post updatePost(@RequestBody Post post) throws PostException {
    	Post p = postDao.findById(post.getPostId());
    	p.setDescription(post.getDescription());
    	p.setTitle(post.getTitle());
    	return postDao.updatePost(p);
    }

    @CrossOrigin(origins="http://localhost:4200")
    @RequestMapping(value= "/posts/{postId}", method= RequestMethod.DELETE)
    public void deletePost(@PathVariable("postId") long postId) throws PostException {
    	Post p = postDao.findById(postId);
    	postDao.deletePost(p);
    }
    
    @CrossOrigin(origins="http://localhost:4200")
    @RequestMapping(value= "/posts/{postId}/addtags", method= RequestMethod.PUT)
    public Post addTags(@PathVariable("postId") long postId, @RequestBody Set<Tag> tags) throws PostException {
    	Post post = postDao.findById(postId);
    	post.setTags(tags);
        return postDao.updatePost(post);
    }
    
    @CrossOrigin(origins="http://localhost:4200")
    @RequestMapping(value = "/posts/{postId}/imageDisplay", method= RequestMethod.GET)
    public void showImage(@PathVariable("postId") long postId, HttpServletResponse response,HttpServletRequest request) 
            throws IOException, PostException{
    	Post post = postDao.findById(postId);
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(post.getFileContent());
        response.getOutputStream().close();
    }
    
    public void sendEmail(String userEmail, String message) {
        try{
		Email email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("gayathri.thammana1837@gmail.com", "Gayathri@264"));
		email.setSSLOnConnect(true);
		
	    email.setFrom(userEmail);//"thammana.g@husky.neu.edu"
	    email.setSubject("Password Reminder");
	    email.setMsg(message);
	    email.addTo(userEmail);
	    email.send();
        } catch(Exception e)
	    {
	      System.out.println("email was not sent" +e.getMessage());
	    }
	}
}
