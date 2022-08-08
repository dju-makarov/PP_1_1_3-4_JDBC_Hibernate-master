package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Util util;
    public UserDaoHibernateImpl() {
        util = new Util();
    }


    @Override
    public void createUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = util.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("create table if not exists users (id  bigint auto_increment primary key, name varchar(30)  null, lastName varchar(30)  null, age tinyint null)");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = util.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists users");
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = util.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            NativeQuery<User> nativeQuery = session.createNativeQuery("insert into users (name, lastName, age) values (?, ?, ?)", User.class);
            nativeQuery.setParameter(1, name);
            nativeQuery.setParameter(2, lastName);
            nativeQuery.setParameter(3, age);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = util.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            NativeQuery<Integer> query = session.createNativeQuery("delete from users where id = (?)", Integer.class);
            query.setParameter(1, id);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        try {
            SessionFactory sessionFactory = util.getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            NativeQuery<User> nativeQuery = session.createNativeQuery("SELECT id, name, lastName, age FROM users", User.class);
            session.getTransaction().commit();
            session.close();
            return nativeQuery.getResultList();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory sessionFactory = util.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM users");
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
