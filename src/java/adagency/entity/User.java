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
@Table(name = "users", catalog = "advertisingagency", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "firstName_Key"),
    @UniqueConstraint(columnNames = "lastName_Key"),
    @UniqueConstraint(columnNames = "address_Key")}
)
public class User implements java.io.Serializable {

    private Integer userId;
    private String username;
    private String email;
    private String password;
    private String firstNameKey;
    private String lastNameKey;
    private String phone;
    private String addressKey;
    private Set<Message> messages = new HashSet<>(0);

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, String firstNameKey, String lastNameKey, String phone, String addressKey, Set<Message> messages) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstNameKey = firstNameKey;
        this.lastNameKey = lastNameKey;
        this.phone = phone;
        this.addressKey = addressKey;
        this.messages = messages;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "userId", unique = true, nullable = false)
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "username", unique = true, nullable = false, length = 45)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "email", unique = true, nullable = false, length = 45)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Column(name = "phone", length = 45)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "address_Key", unique = true)
    public String getAddressKey() {
        return this.addressKey;
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

}
