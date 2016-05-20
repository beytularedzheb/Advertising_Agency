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
@Table(name = "servicecategories", catalog = "advertisingagency", uniqueConstraints = @UniqueConstraint(columnNames = "name_Key")
)
public class ServiceCategory implements java.io.Serializable {

    private Integer serviceCategoryId;
    private String nameKey;
    private Set<Service> services = new HashSet<>(0);

    public ServiceCategory() {
    }

    public ServiceCategory(String nameKey, Set<Service> services) {
        this.nameKey = nameKey;
        this.services = services;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceCategory")
    public Set<Service> getServices() {
        return this.services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

}