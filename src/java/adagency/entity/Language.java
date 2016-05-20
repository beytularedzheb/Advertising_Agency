package adagency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "languages", catalog = "advertisingagency", uniqueConstraints = @UniqueConstraint(columnNames = "languageName")
)
public class Language implements java.io.Serializable {

    private String languageCode;
    private String languageName;

    public Language() {
    }

    public Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public Language(String languageCode, String languageName) {
        this.languageCode = languageCode;
        this.languageName = languageName;
    }

    @Id
    @Column(name = "languageCode", unique = true, nullable = false, length = 2)
    public String getLanguageCode() {
        return this.languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Column(name = "languageName", unique = true)
    public String getLanguageName() {
        return this.languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

}
