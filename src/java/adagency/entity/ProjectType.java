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
@Table(name = "projecttypes", catalog = "advertisingagency", uniqueConstraints = {
    @UniqueConstraint(columnNames = "projecttypeText_Key"),
    @UniqueConstraint(columnNames = "projecttypeCode")}
)
public class ProjectType implements java.io.Serializable {

    private Integer projectTypeId;
    private String projectTypeCode;
    private String projectTypeTextKey;
    private Set<Project> projects = new HashSet<>(0);

    public ProjectType() {
    }

    public ProjectType(String projectTypeCode, String projectTypeTextKey, Set<Project> projects) {
        this.projectTypeCode = projectTypeCode;
        this.projectTypeTextKey = projectTypeTextKey;
        this.projects = projects;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "projecttypeId", unique = true, nullable = false)
    public Integer getProjectTypeId() {
        return this.projectTypeId;
    }

    public void setProjectTypeId(Integer projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    @Column(name = "projecttypeCode", unique = true, length = 45)
    public String getProjectTypeCode() {
        return this.projectTypeCode;
    }

    public void setProjectTypeCode(String projectTypeCode) {
        this.projectTypeCode = projectTypeCode;
    }

    @Column(name = "projecttypeText_Key", unique = true)
    public String getProjectTypeTextKey() {
        return this.projectTypeTextKey;
    }

    public void setProjectTypeTextKey(String projectTypeTextKey) {
        this.projectTypeTextKey = projectTypeTextKey;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectType")
    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}
