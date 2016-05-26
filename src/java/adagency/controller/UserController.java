package adagency.controller;

import adagency.dao.UserDao;
import adagency.entity.User;
import adagency.i18n.Text;
import adagency.util.AbstractHelper;
import adagency.util.Global;
import adagency.util.JsfUtil;
import adagency.util.MD5Hashing;
import adagency.util.SessionUtil;
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
import javax.servlet.http.HttpSession;
import org.apache.commons.configuration.ConfigurationException;

@ManagedBean(name = "userController")
@SessionScoped
public class UserController extends AbstractHelper<User> implements java.io.Serializable {

    private User loggedUser;

    public User getLoggedUser() {
        return loggedUser;
    }
    
    @ManagedProperty(value = "#{propController}")
    private PropController propController;

    public void setPropController(PropController propController) {
        this.propController = propController;
    }

    private final UserDao userDao = new UserDao();

    public UserDao getUserDao() {
        return userDao;
    }

    public User getUserById(Integer id) {
        return getUserDao().find(id);
    }

    @Override
    public List<User> getItems() {
        if (items == null) {
            items = getUserDao().findAll();
        }
        return items;
    }

    @Override
    public User prepareEdit() {
        boolEdit = true;
        boolCreate = false;
        return getSelected();
    }

    @Override
    public User prepareCreate() {
        setSelected(new User());
        firstName = "";
        lastName = "";
        address = "";
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
                        if (getSelected().getUserId() == null) {
                            getUserDao().create(getSelected());
                            boolEdit = true;
                            boolCreate = false;
                        }
                    // notice that CREATE does not have break statement!
                    case UPDATE:
                        if (shouldUpdate(firstName, getSelected().getFirstNameKey())) {
                            getSelected().setFirstNameKey(generateKey("userFirstName", getSelected().getUserId().toString()));
                        }
                        if (shouldUpdate(lastName, getSelected().getLastNameKey())) {
                            getSelected().setLastNameKey(generateKey("userLastName", getSelected().getUserId().toString()));
                        }
                        if (shouldUpdate(address, getSelected().getAddressKey())) {
                            getSelected().setAddressKey(generateKey("userAddress", getSelected().getUserId().toString()));
                        }

                        propController.addPropertyBySelectedLang(getSelected().getFirstNameKey(), firstName);
                        propController.addPropertyBySelectedLang(getSelected().getLastNameKey(), lastName);
                        propController.addPropertyBySelectedLang(getSelected().getAddressKey(), address);

                        getUserDao().update(getSelected());

                        break;
                    case DELETE:
                        propController.removeProperty(getSelected().getFirstNameKey());
                        propController.removeProperty(getSelected().getLastNameKey());
                        propController.removeProperty(getSelected().getAddressKey());
                        getUserDao().delete(getSelected());
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

    private String username;
    private String password;

    public String login() {
        if (getUserDao().validate(username, MD5Hashing.getMD5(password))) {
            
            HttpSession session = SessionUtil.getSession();
            session.setAttribute(Global.SESSION_USERNAME, getUsername());
            loggedUser = getUserDao().findUserByUsername(getUsername());
            if (loggedUser == null) {
                loggedUser = getUserDao().findUserByEmail(getUsername());
            }
            
            this.username = null;
            this.password = null;
            
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(Text.BUNDLE_NAME).getString("Welcome") + " " + loggedUser.getUsername());
        }
        else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle(Text.BUNDLE_NAME).getString("InvalidUsernameOrPassword"));
            return null;
        }
        
        if (loggedUser != null) {            
            return Global.ADMIN_DEFAULT_PAGE + "?faces-redirect=true";
        }
        return Global.ADMIN_LOGIN_PAGE + "?faces-redirect=true";
    }
    
    public String logout() {
        HttpSession session = SessionUtil.getSession();
        session.invalidate();
        loggedUser = null;
        
        return Global.ADMIN_LOGIN_PAGE + "?faces-redirect=true";
    } 

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUserById(Integer.valueOf(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return o.getUserId().toString();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), User.class.getName()});
                return null;
            }
        }
    }

    /*------------------------------------------------------------------------*/
    String firstName;
    String lastName;
    String address;

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

    public String getAddress() {
        if (boolEdit && !boolCreate) {
            address = propController.getValueBySelectedLang(selected.getAddressKey());
        }
        return address;
    }

    public void setAddress(String address) throws ConfigurationException {
        this.address = address;
    }
    /*------------------------------------------------------------------------*/
}
