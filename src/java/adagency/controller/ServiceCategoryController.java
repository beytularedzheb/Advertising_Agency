package adagency.controller;

import adagency.dao.ServiceCategoryDao;
import adagency.entity.ServiceCategory;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "serviceCategoryController")
@SessionScoped
public class ServiceCategoryController implements java.io.Serializable {

    private final ServiceCategoryDao serviceCategoryDao = new ServiceCategoryDao();;
    
    public List<ServiceCategory> findAll() {
        return serviceCategoryDao.findAll();
    }

}
