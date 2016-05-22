package adagency.controller;

import adagency.dao.LanguageDao;
import adagency.entity.Language;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "languageController")
@SessionScoped
public class LanguageController implements java.io.Serializable {

    private Language language;

    private final LanguageDao languageDao = new LanguageDao();
    
    public LanguageController() {
        List<Language> lng = this.findAll();
        language = lng != null ? lng.get(0) : null;
    }
    
    public List<Language> findAll() {
        return languageDao.findAll();
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    
}
