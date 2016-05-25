package adagency.controller;

import adagency.dao.ProjectDao;
import adagency.entity.Project;
import adagency.i18n.Text;
import adagency.util.AbstractHelper;
import adagency.util.JsfUtil;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.commons.configuration.ConfigurationException;

@ManagedBean(name = "projectController")
@SessionScoped
public class ProjectController extends AbstractHelper<Project> implements java.io.Serializable {

    @ManagedProperty(value = "#{propController}")
    private PropController propController;
    private final ProjectDao projectDao = new ProjectDao();

    public void setPropController(PropController propController) {
        this.propController = propController;
    }

    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public Project getProjectById(Integer id) {
        return projectDao.find(id);
    }

    @Override
    public List<Project> getItems() {
        if (items == null) {
            items = getProjectDao().findAll();
        }
        return items;
    }

    @Override
    public Project prepareEdit() {
        boolEdit = true;
        boolCreate = false;
        return getSelected();
    }

    @Override
    public Project prepareCreate() {
        setSelected(new Project());
        name = "";
        description = "";
        boolEdit = false;
        boolCreate = true;
        return getSelected();
    }

    @Override
    public void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (getSelected() != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        if (getSelected().getProjectId() == null) {
                            getProjectDao().create(getSelected());
                            boolEdit = true;
                            boolCreate = false;
                        }
                    // notice that CREATE does not have break statement!
                    case UPDATE:
                        if (shouldUpdate(name, getSelected().getNameKey())) {
                            getSelected().setNameKey(generateKey("projectName", getSelected().getProjectId().toString()));
                        }
                        if (shouldUpdate(description, getSelected().getDescriptionKey())) {
                            getSelected().setDescriptionKey(generateKey("projectDescription", getSelected().getProjectId().toString()));
                        }
                        propController.addPropertyBySelectedLang(getSelected().getNameKey(), name);
                        propController.addPropertyBySelectedLang(getSelected().getDescriptionKey(), description);

                        getProjectDao().update(getSelected());

                        break;
                    case DELETE:
                        propController.removeProperty(getSelected().getNameKey());
                        propController.removeProperty(getSelected().getDescriptionKey());
                        getProjectDao().delete(getSelected());
                        break;
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (Exception ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle(Text.BUNDLE_NAME).getString("ErrorOccured"));
                }
            }
        }
    }

    @FacesConverter(forClass = Project.class)
    public static class ProjectControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProjectController controller = (ProjectController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "projectController");
            return controller.getProjectById(Integer.valueOf(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Project) {
                Project o = (Project) object;
                return o.getProjectId().toString();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Project.class.getName()});
                return null;
            }
        }

    }

    /*------------------------------------------------------------------------*/
    private String name;
    private String description;

    public String getName() {
        if (boolEdit && !boolCreate) {
            name = propController.getValueBySelectedLang(getSelected().getNameKey());
        }

        return name;
    }

    public void setName(String name) throws ConfigurationException {
        this.name = name;
    }

    public String getDescription() {
        if (boolEdit && !boolCreate) {
            description = propController.getValueBySelectedLang(getSelected().getDescriptionKey());
        }

        return description;
    }

    public void setDescription(String description) throws ConfigurationException {
        this.description = description;
    }
    /*------------------------------------------------------------------------*/

}
