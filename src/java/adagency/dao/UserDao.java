package adagency.dao;

import adagency.entity.User;

public class UserDao extends AbstractDao<User> {

    public UserDao() {
        super(User.class);
    }
}
