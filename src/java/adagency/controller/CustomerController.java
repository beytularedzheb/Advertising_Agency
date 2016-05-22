package adagency.controller;

import adagency.dao.CustomerDao;
import adagency.entity.Customer;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "customerController")
@SessionScoped
public class CustomerController implements java.io.Serializable {

    private final CustomerDao customerDao = new CustomerDao();
    
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public List<Customer> findTop(int top) {
        return customerDao.findTop(top);
    }
}
