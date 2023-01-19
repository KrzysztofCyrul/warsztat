package com.kris.warsztat.dao;

import com.kris.warsztat.model.user;
import com.kris.warsztat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserDao {

    public user getConnectedUser(String userName, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<user> query = session.createQuery("SELECT u FROM user u WHERE userName = :name AND password = :pass", user.class );
            query.setParameter("name", userName);
            query.setParameter("pass", password);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            System.err.println("User not found");
            return null;
        }
    }

    public void createUser(user user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public user getUser(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(user.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
