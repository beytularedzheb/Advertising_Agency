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
@Table(name = "servicecategories", catalog = "advertisingagency", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name_Key"),
    @UniqueConstraint(columnNames = "description_Key")}
)
public class ServiceCategory implements java.io.Serializable {

    private Integer serviceCategoryId;
    private String nameKey;
    private String descriptionKey;
    private Set<Service> services = new HashSet<>(0);

    public ServiceCategory() {
    }

    public ServiceCategory(String nameKey, String descriptionKey, Set<Service> services) {
        this.nameKey = nameKey;
        this.services = services;
        this.descriptionKey = descriptionKey;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "serviceCategoryId", unique = true, nullable = false)
    public Integer getServiceCategoryId() {
        return this.serviceCategoryId;
    }

    public void setServiceCategoryId(Integer serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    @Column(name = "name_Key", unique = true)
    public String getNameKey() {
        return this.nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    @Column(name = "description_Key", unique = true)
    public String getDescriptionKey() {
        return descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "serviceCategory")
    public Set<Service> getServices() {
        return this.services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceCategoryId != null ? serviceCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceCategory)) {
            return false;
        }
        ServiceCategory other = (ServiceCategory) object;
        if ((this.serviceCategoryId == null && other.serviceCategoryId != null) || (this.serviceCategoryId != null && !this.serviceCategoryId.equals(other.serviceCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "adagency.entity.ServiceCategory[ id=" + serviceCategoryId + " ]";
    }   
}
