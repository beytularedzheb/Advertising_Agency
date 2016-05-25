package adagency.controller;

import adagency.dao.ServiceCategoryDao;
import adagency.entity.ServiceCategory;
import adagency.i18n.Text;
import adagency.util.AbstractHelper;
import adagency.util.JsfUtil;
import adagency.util.JsfUtil.PersistAction;
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

@ManagedBean(name = "serviceCategoryController")
@SessionScoped
public class ServiceCategoryController extends AbstractHelper<ServiceCategory> implements java.io.Serializable {

    @ManagedProperty(value = "#{propController}")
    private PropController propController;

    private final ServiceCategoryDao serviceCategoryDao = new ServiceCategoryDao();

    public ServiceCategory getServiceCategoryById(Integer id) {
        return getServiceCategoryDao().find(id);
    }

    @Override
    public List<ServiceCategory> getItems() {
        if (items == null) {
            items = getServiceCategoryDao().findAll();
        }
        return items;
    }

    public void setPropController(PropController propController) {
        this.propController = propController;
    }

    public ServiceCategoryDao getServiceCategoryDao() {
        return serviceCategoryDao;
    }

    @Override
    public ServiceCategory prepareEdit() {
        boolEdit = true;
        boolCreate = false;
        return getSelected();
    }

    @Override
    public ServiceCategory prepareCreate() {
        setSelected(new ServiceCategory());
        name = "";
        description = "";
        boolEdit = false;
        boolCreate = true;
        return getSelected();
    }

    @Override
    public void persist(PersistAction persistAction, String successMessage) {
        if (getSelected() != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        if (getSelected().getServiceCategoryId() == null) {
                            getServiceCategoryDao().create(getSelected());
                            boolEdit = true;
                            boolCreate = false;
                        }
                    // notice that CREATE does not have break statement!
                    case UPDATE:
                        if (shouldUpdate(name, getSelected().getNameKey())) {
                            getSelected().setNameKey(generateKey("serviceCategoryName", getSelected().getServiceCategoryId().toString()));
                        }
                        if (shouldUpdate(description, getSelected().getDescriptionKey())) {
                            getSelected().setDescriptionKey(generateKey("serviceCategoryDescription", getSelected().getServiceCategoryId().toString()));
                        }
                        propController.addPropertyBySelectedLang(getSelected().getNameKey(), name);
                        propController.addPropertyBySelectedLang(getSelected().getDescriptionKey(), description);

                        getServiceCategoryDao().update(getSelected());

                        break;
                    case DELETE:
                        propController.removeProperty(getSelected().getNameKey());
                        propController.removeProperty(getSelected().getDescriptionKey());
                        getServiceCategoryDao().delete(getSelected());
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

    @FacesConverter(forClass = ServiceCategory.class)
    public static class ServiceCategoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ServiceCategoryController controller = (ServiceCategoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "serviceCategoryController");
            return controller.getServiceCategoryById(Integer.valueOf(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ServiceCategory) {
                ServiceCategory o = (ServiceCategory) object;
                return o.getServiceCategoryId().toString();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ServiceCategory.class.getName()});
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
