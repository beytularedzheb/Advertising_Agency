package adagency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "languages", catalog = "advertisingagency", uniqueConstraints = @UniqueConstraint(columnNames = "languageName_Key")
)
public class Language implements java.io.Serializable {

    private String languageCode;
    private String languageNameKey;

    public Language() {
    }

    public Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public Language(String languageCode, String languageNameKey) {
        this.languageCode = languageCode;
        this.languageNameKey = languageNameKey;
    }

    @Id
    @Column(name = "languageCode", unique = true, nullable = false, length = 2)
    public String getLanguageCode() {
        return this.languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Column(name = "languageName_Key", unique = true)
    public String getLanguageNameKey() {
        return this.languageNameKey;
    }

    public void setLanguageNameKey(String languageNameKey) {
        this.languageNameKey = languageNameKey;
    }

}
