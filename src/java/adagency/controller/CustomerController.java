package adagency.controller;

import adagency.dao.CustomerDao;
import adagency.entity.Customer;
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

@ManagedBean(name = "customerController")
@SessionScoped
public class CustomerController extends AbstractHelper<Customer> implements java.io.Serializable {

    @ManagedProperty(value = "#{propController}")
    private PropController propController;

    public void setPropController(PropController propController) {
        this.propController = propController;
    }

    private final CustomerDao customerDao = new CustomerDao();

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public List<Customer> findTop(int top) {
        return getCustomerDao().findTop(top);
    }

    public Customer getCustomerById(Integer id) {
        return getCustomerDao().find(id);
    }

    @Override
    public List<Customer> getItems() {
        if (items == null) {
            items = getCustomerDao().findAll();
        }
        return items;
    }

    @Override
    public Customer prepareEdit() {
        boolEdit = true;
        boolCreate = false;
        return getSelected();
    }

    @Override
    public Customer prepareCreate() {
        setSelected(new Customer());
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
                        if (getSelected().getCustomerId() == null) {
                            getCustomerDao().create(getSelected());
                            boolEdit = true;
                            boolCreate = false;
                        }
                    // notice that CREATE does not have break statement!
                    case UPDATE:
                        if (shouldUpdate(name, getSelected().getNameKey())) {
                            getSelected().setNameKey(generateKey("customerName", getSelected().getCustomerId().toString()));
                        }
                        if (shouldUpdate(description, getSelected().getDescriptionKey())) {
                            getSelected().setDescriptionKey(generateKey("customerDescription", getSelected().getCustomerId().toString()));
                        }
                        propController.addPropertyBySelectedLang(getSelected().getNameKey(), name);
                        propController.addPropertyBySelectedLang(getSelected().getDescriptionKey(), description);

                        getCustomerDao().update(getSelected());

                        break;
                    case DELETE:
                        propController.removeProperty(getSelected().getNameKey());
                        propController.removeProperty(getSelected().getDescriptionKey());
                        getCustomerDao().delete(getSelected());
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

    @FacesConverter(forClass = Customer.class)
    public static class CustomerControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CustomerController controller = (CustomerController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "customerController");
            return controller.getCustomerById(Integer.valueOf(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Customer) {
                Customer o = (Customer) object;
                return o.getCustomerId().toString();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Customer.class.getName()});
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if (boolEdit && !boolCreate) {
            description = propController.getValueBySelectedLang(getSelected().getDescriptionKey());
        }

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    /*------------------------------------------------------------------------*/
}
