package adagency.controller;

import adagency.dao.EmployeeDao;
import adagency.entity.Employee;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "employeeController")
@SessionScoped
public class EmployeeController implements java.io.Serializable {

    private final EmployeeDao employeeDao = new EmployeeDao();
    
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }
}
