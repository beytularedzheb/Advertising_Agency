package adagency.controller;

import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

@ManagedBean(name = "propController", eager = true)
@SessionScoped
public class PropController implements java.io.Serializable {

    @ManagedProperty(value = "#{languageController}")
    private LanguageController langController;
    private HashMap<String, PropertiesConfiguration> props;

    @PostConstruct
    public void init() {
        props = new HashMap<>();

        langController.getItems().stream().forEach((language) -> {
            try {
                PropertiesConfiguration prop = new PropertiesConfiguration();
                prop.setDelimiterParsingDisabled(true);
                prop.load("adagency/i18n/dbtext_" + language.getLanguageCode() + ".properties");
                prop.setReloadingStrategy(new FileChangedReloadingStrategy());
                
                props.put(language.getLanguageCode(), prop);
            } catch (ConfigurationException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    public void setLangController(LanguageController langController) {
        this.langController = langController;
    }

    public String getValueByCurrentLang(String key) {
        PropertiesConfiguration p = props.get(langController.getLanguage().getLanguageCode());
        return p.getString(key, null);
    }

    public String getValueBySelectedLang(String key) {
        PropertiesConfiguration p = props.get(langController.getSelectOneLanguage().getLanguageCode());
        return p.getString(key, null);
    }

    public void addPropertyBySelectedLang(String key, String value) throws ConfigurationException {
        PropertiesConfiguration p = props.get(langController.getSelectOneLanguage().getLanguageCode());

        if ((value != null && !value.isEmpty()) 
                && (key != null && !key.isEmpty()) 
                && (!p.containsKey(key) || !p.getString(key).equals(value))) {
            p.setProperty(key, value);
            p.save(p.getBasePath());
        }
    }

    public void removeProperty(String key) throws ConfigurationException {
        if (key != null && !key.isEmpty()) {
            for (String p : props.keySet()) {
                PropertiesConfiguration pc = props.get(p);
                if (pc.containsKey(key)) {
                    pc.clearProperty(key);
                    pc.save(pc.getBasePath());
                }
            }
        }
    }
}
