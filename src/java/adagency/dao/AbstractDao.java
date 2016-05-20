package adagency.dao;

import adagency.util.HibernateUtil;

import java.io.*;
import java.util.*;
import org.hibernate.*;

public abstract class AbstractDao<T> {

    private Class<T> entityClass;
    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public AbstractDao() {
    }

    public List<T> findAll() {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            return s.createQuery("from " + entityClass.getName()).list();
        } catch (RuntimeException re) {
            return null;
        } finally {
            s.close();
        }
    }

    public void update(T instance) {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            s.merge(instance);
            s.getTransaction().commit();
        } catch (RuntimeException re) {
            s.getTransaction().rollback();
            throw re;
        } finally {
            s.close();
        }
    }

    public void delete(T instance) {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            s.delete(instance);
            s.getTransaction().commit();
        } catch (RuntimeException re) {
            s.getTransaction().rollback();
            throw re;
        } finally {
            s.close();
        }
    }

    public void create(T instance) {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            s.persist(instance);
            s.getTransaction().commit();
        } catch (RuntimeException re) {
            s.getTransaction().rollback();
            throw re;
        } finally {
            s.close();
        }
    }

    public T find(Object primarykey) {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            return (T) s.get(entityClass, (Serializable) primarykey);
        } catch (RuntimeException re) {
            return null;
        } finally {
            s.close();
        }
    }

    public List<T> findTop(int topNumber) {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            return s.createQuery("from " + entityClass.getName()).setMaxResults(topNumber).list();
        } catch (RuntimeException re) {
            return null;
        } finally {
            s.close();
        }
    }
}
