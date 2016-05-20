package adagency.controller;

import adagency.dao.ProjectDao;
import adagency.entity.Project;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "projectController")
@SessionScoped
public class ProjectController {

    private final ProjectDao projectDao = new ProjectDao();;
    
    public List<Project> findAll() {
        return projectDao.findAll();
    }

}
