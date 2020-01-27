package com.webtools.huskyanswerswt.dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.webtools.huskyanswerswt.exception.FollowTagsException;
import com.webtools.huskyanswerswt.pojo.FollowedTag;

@Repository
public class FollowTagsDAO extends DAO{
	
		// Method to generate the dummy posts records.
	    public List<FollowedTag> getTagsByUserId(Long id) throws FollowTagsException{
	 
	    	try {
	            begin();
	            Query q = getSession().createQuery("from FollowedTag where userId= :id");
	            q.setLong("id", id);	
	            List<FollowedTag> tags = q.list();
	            commit();
	            return tags;
	        } catch (HibernateException e) {
	            rollback();
	            throw new FollowTagsException("Could not get all tags", e);
	        }
	    }

	    
	    public FollowedTag followTag(FollowedTag tag) throws FollowTagsException{
	    	try {
				begin();
				System.out.println("inside followstag DAO");

				getSession().save(tag);
				commit();
				return tag;

			} catch (HibernateException e) {
				rollback();
				throw new FollowTagsException("Exception while following tag: " + e.getMessage());
			} 
	    }
	    
	    public List<String> getUserIds(List<String> tagsList) throws FollowTagsException{
	    	try {
				begin();
//				List<String> ids = Arrays.asList("java", "javascript");
				Query q = getSession().createQuery("SELECT distinct userEmail FROM FollowedTag where tagName IN :ids");	
				q.setParameterList("ids", tagsList);
				List<String> userIds = q.list();
				commit();
				return userIds;
			} catch (HibernateException e) {
				rollback();
				throw new FollowTagsException("Could not get user ids ", e);
			
		}
	    }

}
