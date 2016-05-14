package adagency.entity;

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
@Table(name = "services", catalog = "advertisingagency", uniqueConstraints = @UniqueConstraint(columnNames = "name_Key")
)
public class Service implements java.io.Serializable {

    private Integer serviceId;
    private ServiceCategory serviceCategory;
    private String nameKey;
    private Long price;
    private String productionTime;

    public Service() {
    }

    public Service(ServiceCategory serviceCategory, String nameKey, Long price, String productionTime) {
        this.serviceCategory = serviceCategory;
        this.nameKey = nameKey;
        this.price = price;
        this.productionTime = productionTime;
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

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "price", precision = 10, scale = 0)
    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Column(name = "productionTime", length = 45)
    public String getProductionTime() {
        return this.productionTime;
    }

    public void setProductionTime(String productionTime) {
        this.productionTime = productionTime;
    }

}
