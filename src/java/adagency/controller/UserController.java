package adagency.controller;

import adagency.dao.UserDao;
import adagency.entity.User;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "userController")
@SessionScoped
public class UserController implements java.io.Serializable {

    private final UserDao userDao = new UserDao();;
    
    public List<User> findAll() {
        return userDao.findAll();
    }

}
