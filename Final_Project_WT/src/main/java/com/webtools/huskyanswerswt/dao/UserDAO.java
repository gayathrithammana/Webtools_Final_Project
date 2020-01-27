package com.webtools.huskyanswerswt.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.webtools.huskyanswerswt.exception.PostException;
import com.webtools.huskyanswerswt.exception.UserException;
import com.webtools.huskyanswerswt.pojo.Post;
import com.webtools.huskyanswerswt.pojo.User;
@Repository
public class UserDAO extends DAO {

	public UserDAO() {
	}

	public User findUser(User u) throws UserException {
		
		try {
			begin();
			Query q = getSession().createQuery("from User where userEmail = :useremail");
			q.setString("useremail", u.getUserEmail());
			User user = (User) q.uniqueResult();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + u.getUserEmail(), e);
		}
	}
	
	public List<User> getAllUsers() throws UserException {
		
		try {
			begin();
			Query q = getSession().createQuery("select userEmail,userId from User");			
			List<User> users = q.list();
			return users;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get users ", e);
		}
	}
	
	public long getUserByEmail(String userEmail) throws UserException{
		try {
			begin();
			Query q = getSession().createQuery("select count(*) from User where userEmail = :useremail");
			q.setString("useremail", userEmail);
			Long count = (Long) q.uniqueResult();
			return count;
		}catch(HibernateException e){
			throw new UserException("Could not get user", e);
		}
				
	}
	

	public User register(User u) throws UserException {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(u);
			commit();
			return u;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}
	
	public boolean updateUser(String email) throws UserException {
		try {
			begin();
			System.out.println("inside DAO");
			Query q = getSession().createQuery("from User where userEmail = :useremail");
			q.setString("useremail", email);
			User user = (User) q.uniqueResult();
			if(user!=null){
				getSession().update(user);
				commit();
				return true;
			}else{
				return false;
			}

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	
	}


}