package adagency.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "projects", catalog = "advertisingagency", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name_Key"),
    @UniqueConstraint(columnNames = "description_Key")}
)
public class Project implements java.io.Serializable {

    private Integer projectId;
    private Customer customer;
    private ProjectType projectType;
    private String nameKey;
    private String descriptionKey;
    private String imagePath;
    private Set<Employee> employees = new HashSet<>(0);

    public Project() {
    }

    public Project(Customer customer, ProjectType projectType, String nameKey, String descriptionKey, String imagePath, Set<Employee> employees) {
        this.customer = customer;
        this.projectType = projectType;
        this.nameKey = nameKey;
        this.descriptionKey = descriptionKey;
        this.imagePath = imagePath;
        this.employees = employees;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "projectId", unique = true, nullable = false)
    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId")
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectTypeId")
    public ProjectType getProjectType() {
        return this.projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
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
        return this.descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    @Column(name = "imagePath")
    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "adagency.entity.Project[ id=" + projectId + " ]";
    }
}
