package adagency.controller;

import adagency.dao.ServiceDao;
import adagency.entity.Service;
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

@ManagedBean(name = "serviceController")
@SessionScoped
public class ServiceController extends AbstractHelper<Service> implements java.io.Serializable {

    @ManagedProperty(value = "#{propController}")
    private PropController propController;

    @ManagedProperty(value = "#{serviceCategoryController}")
    private ServiceCategoryController serviceCategoryController;

    public void setServiceCategoryController(ServiceCategoryController serviceCategoryController) {
        this.serviceCategoryController = serviceCategoryController;
    }

    private final ServiceDao serviceDao = new ServiceDao();

    public Service getServiceById(Integer id) {
        return getServiceDao().find(id);
    }

    @Override
    public List<Service> getItems() {
        if (items == null) {
            items = getServiceDao().findAll();
        }
        return items;
    }

    public void setPropController(PropController propController) {
        this.propController = propController;
    }

    public ServiceDao getServiceDao() {
        return serviceDao;
    }

    @Override
    public Service prepareEdit() {
        boolEdit = true;
        boolCreate = false;
        return getSelected();
    }

    @Override
    public Service prepareCreate() {
        setSelected(new Service());
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
                        if (getSelected().getServiceId() == null) {
                            getServiceDao().create(getSelected());
                            boolEdit = true;
                            boolCreate = false;
                        }
                    // notice that CREATE does not have break statement!
                    case UPDATE:
                        if (shouldUpdate(name, getSelected().getNameKey())) {
                            getSelected().setNameKey(generateKey("serviceName", getSelected().getServiceId().toString()));
                        }
                        if (shouldUpdate(description, getSelected().getDescriptionKey())) {
                            getSelected().setDescriptionKey(generateKey("serviceDescription", getSelected().getServiceId().toString()));
                        }
                        propController.addPropertyBySelectedLang(getSelected().getNameKey(), name);
                        propController.addPropertyBySelectedLang(getSelected().getDescriptionKey(), description);

                        getServiceDao().update(getSelected());

                        serviceCategoryController.reloadItems();
                        break;
                    case DELETE:
                        propController.removeProperty(getSelected().getNameKey());
                        propController.removeProperty(getSelected().getDescriptionKey());
                        getServiceDao().delete(getSelected());
                        serviceCategoryController.reloadItems();
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

    @FacesConverter(forClass = Service.class)
    public static class ServiceCategoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ServiceController controller = (ServiceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "serviceController");
            return controller.getServiceById(Integer.valueOf(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Service) {
                Service o = (Service) object;
                return o.getServiceId().toString();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Service.class.getName()});
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
