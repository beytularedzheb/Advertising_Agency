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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "employees", catalog = "advertisingagency", uniqueConstraints = {
    @UniqueConstraint(columnNames = "firstName_Key"),
    @UniqueConstraint(columnNames = "lastName_Key"),
    @UniqueConstraint(columnNames = "description_Key")}
)
public class Employee implements java.io.Serializable {

    private Integer employeeId;
    private String firstNameKey;
    private String lastNameKey;
    private String jobTitleKey;
    private String picturePath;
    private String descriptionKey;
    private Set<Project> projects = new HashSet<>(0);

    public Employee() {
    }

    public Employee(String firstNameKey, String lastNameKey, String jobTitleKey, String picturePath, String descriptionKey, Set<Project> projects) {
        this.firstNameKey = firstNameKey;
        this.lastNameKey = lastNameKey;
        this.jobTitleKey = jobTitleKey;
        this.picturePath = picturePath;
        this.descriptionKey = descriptionKey;
        this.projects = projects;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "employeeId", unique = true, nullable = false)
    public Integer getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Column(name = "firstName_Key", unique = true)
    public String getFirstNameKey() {
        return this.firstNameKey;
    }

    public void setFirstNameKey(String firstNameKey) {
        this.firstNameKey = firstNameKey;
    }

    @Column(name = "lastName_Key", unique = true)
    public String getLastNameKey() {
        return this.lastNameKey;
    }

    public void setLastNameKey(String lastNameKey) {
        this.lastNameKey = lastNameKey;
    }

    @Column(name = "jobTitle_Key")
    public String getJobTitleKey() {
        return this.jobTitleKey;
    }

    public void setJobTitleKey(String jobTitleKey) {
        this.jobTitleKey = jobTitleKey;
    }

    @Column(name = "picturePath")
    public String getPicturePath() {
        return this.picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Column(name = "description_Key", unique = true)
    public String getDescriptionKey() {
        return this.descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employees_projects", catalog = "advertisingagency", joinColumns = {
        @JoinColumn(name = "employeeId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "projectId", nullable = false, updatable = false)})
    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}
