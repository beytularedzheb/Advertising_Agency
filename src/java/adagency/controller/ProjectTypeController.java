package adagency.controller;

import adagency.dao.ProjectTypeDao;
import adagency.entity.ProjectType;
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

@ManagedBean(name = "projectTypeController")
@SessionScoped
public class ProjectTypeController extends AbstractHelper<ProjectType> implements java.io.Serializable {

    @ManagedProperty(value = "#{propController}")
    private PropController propController;
    
    @ManagedProperty(value = "#{projectController}")
    private ProjectController projectController;

    private final ProjectTypeDao projectTypeDao = new ProjectTypeDao();

    public void setPropController(PropController propController) {
        this.propController = propController;
    }

    public ProjectTypeDao getProjectTypeDao() {
        return projectTypeDao;
    }

    public ProjectType getProjectTypeById(Integer id) {
        return getProjectTypeDao().find(id);
    }
    
    public void setProjectController(ProjectController projectController) {
        this.projectController = projectController;
    }
    
    @Override
    public List<ProjectType> getItems() {
        if (items == null) {
            items = getProjectTypeDao().findAll();
        }
        return items;
    }

    @Override
    public ProjectType prepareEdit() {
        boolEdit = true;
        boolCreate = false;
        return getSelected();
    }

    @Override
    public ProjectType prepareCreate() {
        setSelected(new ProjectType());
        projectTypeText = "";
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
                        if (getSelected().getProjectTypeId() == null) {
                            getProjectTypeDao().create(getSelected());
                            boolEdit = true;
                            boolCreate = false;
                        }
                    // notice that CREATE does not have break statement!
                    case UPDATE:
                        if (shouldUpdate(projectTypeText, getSelected().getProjectTypeTextKey())) {
                            getSelected().setProjectTypeTextKey(generateKey("projectTypeText", getSelected().getProjectTypeId().toString()));
                        }

                        propController.addPropertyBySelectedLang(getSelected().getProjectTypeTextKey(), projectTypeText);

                        getProjectTypeDao().update(getSelected());
                        projectController.reloadItems();
                        break;
                    case DELETE:
                        propController.removeProperty(getSelected().getProjectTypeTextKey());
                        getProjectTypeDao().delete(getSelected());
                        projectController.reloadItems();
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
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ResourceBundle rb = FacesContext.getCurrentInstance().getApplication().getResourceBundle(ctx, Text.BUNDLE_VAR_NAME);
                    JsfUtil.addErrorMessage(ex, rb.getString("ErrorOccured"));
                }
            }
        }
    }

    @FacesConverter(forClass = ProjectType.class)
    public static class ProjectTypeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProjectTypeController controller = (ProjectTypeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "projectTypeController");
            return controller.getProjectTypeById(Integer.valueOf(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ProjectType) {
                ProjectType o = (ProjectType) object;
                return o.getProjectTypeId().toString();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProjectType.class.getName()});
                return null;
            }
        }
    }

    /*------------------------------------------------------------------------*/
    private String projectTypeText;

    public String getProjectTypeText() {
        if (boolEdit && !boolCreate) {
            projectTypeText = propController.getValueBySelectedLang(getSelected().getProjectTypeTextKey());
        }
        return projectTypeText;
    }

    public void setProjectTypeText(String projectTypeText) {
        this.projectTypeText = projectTypeText;
    }
    /*------------------------------------------------------------------------*/

}
