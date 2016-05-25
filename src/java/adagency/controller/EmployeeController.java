package adagency.controller;

import adagency.dao.EmployeeDao;
import adagency.entity.Employee;
import adagency.i18n.Text;
import adagency.util.AbstractHelper;
import adagency.util.JsfUtil;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.commons.configuration.ConfigurationException;

@ManagedBean(name = "employeeController")
@SessionScoped
public class EmployeeController extends AbstractHelper<Employee> implements java.io.Serializable {

    @ManagedProperty(value = "#{propController}")
    private PropController propController;

    public void setPropController(PropController propController) {
        this.propController = propController;
    }

    private final EmployeeDao employeeDao = new EmployeeDao();

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    @Override
    public List<Employee> getItems() {
        if (items == null) {
            items = getEmployeeDao().findAll();
        }
        return items;
    }

    @Override
    public Employee prepareEdit() {
        boolEdit = true;
        boolCreate = false;
        return getSelected();
    }

    @Override
    public Employee prepareCreate() {
        setSelected(new Employee());
        firstName = "";
        lastName = "";
        jobTitle = "";
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
                        if (getSelected().getEmployeeId() == null) {
                            getEmployeeDao().create(getSelected());
                            boolEdit = true;
                            boolCreate = false;
                        }
                    // notice that CREATE does not have break statement!
                    case UPDATE:
                        if (shouldUpdate(firstName, getSelected().getFirstNameKey())) {
                            getSelected().setFirstNameKey(generateKey("employeeFirstName", getSelected().getEmployeeId().toString()));
                        }
                        if (shouldUpdate(lastName, getSelected().getLastNameKey())) {
                            getSelected().setLastNameKey(generateKey("employeeLastName", getSelected().getEmployeeId().toString()));
                        }
                        if (shouldUpdate(jobTitle, getSelected().getJobTitleKey())) {
                            getSelected().setJobTitleKey(generateKey("employeeJobTitle", getSelected().getEmployeeId().toString()));
                        }
                        if (shouldUpdate(description, getSelected().getDescriptionKey())) {
                            getSelected().setDescriptionKey(generateKey("employeeDescription", getSelected().getEmployeeId().toString()));
                        }                        
                        propController.addPropertyBySelectedLang(getSelected().getFirstNameKey(), firstName);
                        propController.addPropertyBySelectedLang(getSelected().getLastNameKey(), lastName);
                        propController.addPropertyBySelectedLang(getSelected().getJobTitleKey(), jobTitle);
                        propController.addPropertyBySelectedLang(getSelected().getDescriptionKey(), description);
                        
                        getEmployeeDao().update(getSelected());

                        break;
                    case DELETE:
                        propController.removeProperty(getSelected().getFirstNameKey());
                        propController.removeProperty(getSelected().getLastNameKey());
                        propController.removeProperty(getSelected().getJobTitleKey());
                        propController.removeProperty(getSelected().getDescriptionKey());
                        getEmployeeDao().delete(getSelected());
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

    /*------------------------------------------------------------------------*/
    String firstName;
    String lastName;
    String jobTitle;
    String description;

    public String getFirstName() {
        if (boolEdit && !boolCreate) {
            firstName = propController.getValueBySelectedLang(selected.getFirstNameKey());
        }
        return firstName;
    }

    public void setFirstName(String firstName) throws ConfigurationException {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (boolEdit && !boolCreate) {
            lastName = propController.getValueBySelectedLang(selected.getLastNameKey());
        }
        return lastName;
    }

    public void setLastName(String lastName) throws ConfigurationException {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        if (boolEdit && !boolCreate) {
            jobTitle = propController.getValueBySelectedLang(selected.getJobTitleKey());
        }
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) throws ConfigurationException {
        this.jobTitle = jobTitle;
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
