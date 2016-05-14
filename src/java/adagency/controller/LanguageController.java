package adagency.controller;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LanguageController {

    private Locale locale = new Locale("bg");

    private String nameKey = "test";

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        this.locale = new Locale(language);
    }

    public String getNameKey() {
        return nameKey;
    }
}
