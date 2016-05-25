package adagency.controller;

import adagency.dao.LanguageDao;
import adagency.entity.Language;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "languageController", eager = true)
@SessionScoped
public class LanguageController implements java.io.Serializable {

    private Language selectOneLanguage; 
    
    private Language language;
    private List<Language> items = null;
    private final LanguageDao languageDao = new LanguageDao();
    
    public LanguageController() {
        // set default language
        language = getItems().get(0);
    }
    
    public Language getSelectOneLanguage() {
        if (selectOneLanguage == null) {
            selectOneLanguage = getLanguage();
        }
        return selectOneLanguage;
    }

    public void setSelectOneLanguage(Language selectOneLanguage) {
        this.selectOneLanguage = selectOneLanguage;
    }

    public List<Language> getItems() {
        if (items == null) {
            items = languageDao.findAll();
        }
        return items;
    }
    
    public Language getLanguageById(String id) {
        return languageDao.find(id);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @FacesConverter(forClass = Language.class)
    public static class LanguageControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LanguageController controller = (LanguageController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "languageController");
            return controller.getLanguageById(value);
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Language) {
                Language o = (Language) object;
                return o.getLanguageCode();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Language.class.getName()});
                return null;
            }
        }

    }

}
