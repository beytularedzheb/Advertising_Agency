package adagency.controller;

import adagency.dao.MessageDao;
import adagency.entity.Message;
import adagency.i18n.Text;
import adagency.util.AbstractHelper;
import adagency.util.JsfUtil;
import adagency.util.JsfUtil.PersistAction;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "messageController")
@SessionScoped
public class MessageController extends AbstractHelper<Message> implements java.io.Serializable {

    private final MessageDao messageDao = new MessageDao();

    private Message message = new Message();

    public Message getMessageById(Integer id) {
        return getMessageDao().find(id);
    }

    @Override
    public List<Message> getItems() {
        if (items == null) {
            items = getMessageDao().findAll();
        }
        return items;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    @Override
    public Message prepareEdit() {
        boolEdit = true;
        boolCreate = false;
        return getSelected();
    }

    @Override
    public Message prepareCreate() {
        setSelected(new Message());
        boolEdit = false;
        boolCreate = true;
        return getSelected();
    }

    public void sendMessage() {

        if (validate(message)) {
            getMessageDao().create(message);

            FacesContext ctx = FacesContext.getCurrentInstance();
            ResourceBundle rb = FacesContext.getCurrentInstance().getApplication().getResourceBundle(ctx, Text.BUNDLE_VAR_NAME);

            ctx.addMessage(":mygrowl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            rb.getString("SuccessfullySentMessage"),
                            ""));
            RequestContext.getCurrentInstance().update("mygrowl");

            message = new Message();
        }
    }

    @Override
    public void persist(PersistAction persistAction, String successMessage) {
        if (getSelected() != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                        if (validate(getSelected())) {
                            if (getSelected().getMessageId() == null) {
                                getMessageDao().create(getSelected());
                                boolEdit = true;
                                boolCreate = false;
                            } else {
                                getMessageDao().update(getSelected());
                            }
                        }
                        break;
                    case UPDATE:
                        getMessageDao().update(getSelected());
                        break;
                    case DELETE:
                        getMessageDao().delete(getSelected());
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

    private boolean validate(Message message) {
        return (message != null
                && message.getMessageId() == null
                && !StringUtils.isBlank(message.getSenderName())
                && !StringUtils.isBlank(message.getSenderEmail())
                && !StringUtils.isBlank(message.getContent())
                && !StringUtils.isBlank(message.getSubject()));
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @FacesConverter(forClass = Message.class)
    public static class ServiceCategoryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MessageController controller = (MessageController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "messageController");
            return controller.getMessageById(Integer.valueOf(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Message) {
                Message o = (Message) object;
                return o.getMessageId().toString();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Message.class.getName()});
                return null;
            }
        }

    }
}
