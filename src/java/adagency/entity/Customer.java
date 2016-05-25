package adagency.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "customers", catalog = "advertisingagency", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name_Key"),
    @UniqueConstraint(columnNames = "description_Key")}
)
public class Customer implements java.io.Serializable {

    private Integer customerId;
    private String nameKey;
    private String logoPath;
    private String webAddress;
    private String descriptionKey;
    private Set<Project> projects = new HashSet<>(0);

    public Customer() {
    }

    public Customer(String nameKey, String logoPath, String webAddress, String descriptionKey, Set<Project> projects) {
        this.nameKey = nameKey;
        this.logoPath = logoPath;
        this.webAddress = webAddress;
        this.descriptionKey = descriptionKey;
        this.projects = projects;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "customerId", unique = true, nullable = false)
    public Integer getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Column(name = "name_Key", unique = true)
    public String getNameKey() {
        return this.nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    @Column(name = "logoPath")
    public String getLogoPath() {
        return this.logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Column(name = "webAddress")
    public String getWebAddress() {
        /*if (this.webAddress != null && !this.webAddress.isEmpty()) {
            return this.webAddress;
        }*/
        return this.webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    @Column(name = "description_Key", unique = true)
    public String getDescriptionKey() {
        return this.descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "adagency.entity.Customer[ id=" + customerId + " ]";
    }

}
