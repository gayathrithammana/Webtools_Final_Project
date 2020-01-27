package com.webtools.huskyanswerswt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.webtools.huskyanswerswt.exception.AnswerException;
import com.webtools.huskyanswerswt.exception.PostException;
import com.webtools.huskyanswerswt.pojo.Answer;
import com.webtools.huskyanswerswt.pojo.Post;

@Repository
public class AnswersDAO extends DAO{
	// Method to generate the dummy posts records.
    public List<Answer> getAnswersByPostId(Long id) throws AnswerException{
 
    	try {
            begin();
            Query q = getSession().createQuery("from Answer where postId= :id");
            q.setLong("id", id);	
            List<Answer> answers = q.list();
            commit();
            return answers;
        } catch (HibernateException e) {
            rollback();
            throw new AnswerException("Could not get all answers", e);
        }
    }
    
    public Answer saveAnswer(Answer answer) throws AnswerException{
    	try {
			begin();
			System.out.println("inside DAO");

			getSession().save(answer);
			commit();
			return answer;

		} catch (HibernateException e) {
			rollback();
			throw new AnswerException("Exception while creating answer: " + e.getMessage());
		}
    }
}
