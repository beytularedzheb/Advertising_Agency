package adagency.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "services", catalog = "advertisingagency", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name_Key"),
    @UniqueConstraint(columnNames = "description_Key")}
)
public class Service implements java.io.Serializable {

    private Integer serviceId;
    private ServiceCategory serviceCategory;
    private String nameKey;
    private BigDecimal price;
    private Integer productionTime;
    private String descriptionKey;

    public Service() {
    }

    public Service(ServiceCategory serviceCategory, String nameKey, BigDecimal price, Integer productionTime, String descriptionKey) {
        this.serviceCategory = serviceCategory;
        this.nameKey = nameKey;
        this.price = price;
        this.productionTime = productionTime;
        this.descriptionKey = descriptionKey;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "serviceId", unique = true, nullable = false)
    public Integer getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceCategoryId")
    public ServiceCategory getServiceCategory() {
        return this.serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    @Column(name = "name_Key", unique = true)
    public String getNameKey() {
        return this.nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    @Column(name = "price", precision = 10, scale = 2)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "productionTime")
    public Integer getProductionTime() {
        return this.productionTime;
    }

    public void setProductionTime(Integer productionTime) {
        this.productionTime = productionTime;
    }

    @Column(name = "description_Key", unique = true)
    public String getDescriptionKey() {
        return descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceId != null ? serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.serviceId == null && other.serviceId != null) || (this.serviceId != null && !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "adagency.entity.Service[ id=" + serviceId + " ]";
    }
    
    /*------------------------------------------------------------------------*/
    /*public String getName() {
        return Prop.getValueBySelectedLang(nameKey);
    }

    public void setName(String name) throws ConfigurationException {
        Prop.addPropertyBySelectedLang(nameKey, name);
    }

    public String getDescription() {
        return Prop.getValueBySelectedLang(descriptionKey);
    }

    public void setDescription(String description) throws ConfigurationException {
        Prop.addPropertyBySelectedLang(descriptionKey, description);
    }*/
    /*------------------------------------------------------------------------*/    
}
