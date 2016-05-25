package adagency.util;

import adagency.i18n.Text;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractHelper<T> implements java.io.Serializable {

    protected boolean boolEdit, boolCreate;
    protected List<T> items = null;
    protected T selected;

    public abstract List<T> getItems();

    public abstract T prepareEdit();

    public abstract T prepareCreate();

    public abstract void persist(JsfUtil.PersistAction persistAction, String successMessage);

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle(Text.BUNDLE_NAME).getString("CreatedMessage"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle(Text.BUNDLE_NAME).getString("UpdatedMessage"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle(Text.BUNDLE_NAME).getString("DeletedMessage"));
        if (!JsfUtil.isValidationFailed()) {
            setSelected(null); // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    protected String generateKey(String... keyComponents) {
        StringBuilder sb = new StringBuilder();
        for (String keyComponent : keyComponents) {
            sb.append(keyComponent);
        }
        return sb.toString();
    }

    protected boolean shouldUpdate(String field, String fieldKey) {
        return ((fieldKey == null || fieldKey.isEmpty()) && field != null && !field.isEmpty());
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }
}
