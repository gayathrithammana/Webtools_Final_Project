package com.webtools.huskyanswerswt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.webtools.huskyanswerswt.exception.PostException;
import com.webtools.huskyanswerswt.pojo.Post;

@Repository
public class PostsDAO extends DAO{
	
    public List<Post> getAllPosts() throws PostException{
    	
    	try {
            begin();
            Query q = getSession().createQuery("from Post order by createDateTime desc");
            List<Post> posts = q.list();
            commit();
            return posts;
        } catch (HibernateException e) {
            rollback();
            throw new PostException("Could not get all posts", e);
        }
    	
    }
    
    public long getAllPostsSize() throws PostException{
    	
    	try {
            begin();
            Query q = getSession().createQuery("select count(*) from Post");
            Long count = (Long) q.uniqueResult();
            commit();
            return count;
        } catch (HibernateException e) {
            rollback();
            throw new PostException("Could not get all posts", e);
        }
    	
    }
    
    public long getAllUserPostsSize(long userId) throws PostException{
    	
    	try {
            begin();
            Query q = getSession().createQuery("select count(*) from Post where userId= :userId");
            q.setLong("userId", userId);
            Long count = (Long) q.uniqueResult();
            commit();
            return count;
        } catch (HibernateException e) {
            rollback();
            throw new PostException("Could not get all posts", e);
        }
    	
    }
    
    public List<Post> getPostsPerPage(int pageNumber, int pageSize) throws PostException{
    	
    	try {
            begin();
            Query q = getSession().createQuery("from Post order by createDateTime desc");
            q.setFirstResult((pageNumber - 1) * pageSize);
            q.setMaxResults(pageSize);
            List<Post> posts = q.list();
            commit();
            return posts;
        } catch (HibernateException e) {
            rollback();
            throw new PostException("Could not get all posts", e);
        }
    	
    }
    
    public List<Post> getUserPostsPerPage(long userId, int pageNumber, int pageSize) throws PostException{
    	
    	try {
            begin();
            Query q = getSession().createQuery("from Post where userId= :userId order by createDateTime desc");
            q.setLong("userId", userId);	
            q.setFirstResult((pageNumber - 1) * pageSize);
            q.setMaxResults(pageSize);
            List<Post> posts = q.list();
            commit();
            return posts;
        } catch (HibernateException e) {
            rollback();
            throw new PostException("Could not get all posts", e);
        }
    	
    }
    
    
    
    public Post findById(Long id) throws PostException{
    	try {
			begin();
			Query q = getSession().createQuery("from Post where postId= :id");
			q.setLong("id", id);		
			Post post = (Post) q.uniqueResult();
			commit();
			return post;
		} catch (HibernateException e) {
			rollback();
			throw new PostException("Could not get post " + id, e);
		}
    }
    
    public Post createPost(Post post) throws PostException{
    	try {
			begin();
			System.out.println("inside Post DAO");

			getSession().save(post);
			commit();
			
			return post;

		} catch (HibernateException e) {
			rollback();
			throw new PostException("Exception while creating post: " + e.getMessage());
		} 
    }
    
    public Post updatePost(Post post) throws PostException{
    	try {
			begin();
			System.out.println("inside Post DAO");

			getSession().update(post);
			commit();
			return post;

		} catch (HibernateException e) {
			rollback();
			throw new PostException("Exception while creating post: " + e.getMessage());
		} 
    }
    
    public void deletePost(Post post) throws PostException {
        try {
            begin();
            getSession().delete(post);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new PostException("Could not delete post", e);
        }
    }
    
}
