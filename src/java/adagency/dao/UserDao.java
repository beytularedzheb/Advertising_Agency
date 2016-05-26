package adagency.dao;

import adagency.entity.User;
import org.hibernate.Session;

public class UserDao extends AbstractDao<User, Integer> {

    public User findUserByUsername(String username) {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            return (User) s.createQuery("from " + User.class.getName() + " u where u.username = :usernamee")
                    .setParameter("usernamee", username)
                    .uniqueResult();
        } catch (RuntimeException re) {
            return null;
        } finally {
            s.close();
        }
    }
    
   public User findUserByEmail(String email) {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            return (User) s.createQuery("from " + User.class.getName() + " u where u.email = :emaill")
                    .setParameter("emaill", email)
                    .uniqueResult();
        } catch (RuntimeException re) {
            return null;
        } finally {
            s.close();
        }
    }
   
    public boolean validate(String username, String password) {
        Session s = sessionFactory.openSession();
        try {
            if (!s.getTransaction().isActive()) {
                s.getTransaction().begin();
            }
            String strQuery = "from " + User.class.getName() + " u where (u.username = :usernamee or u.email = :usernamee) and u.password = :passwordd";

            User user = (User) s.createQuery(strQuery)
                    .setParameter("usernamee", username)
                    .setParameter("passwordd", password).uniqueResult();
            return user != null;
        } catch (RuntimeException re) {
            return false;
        } finally {
            s.close();
        }
    }
}
