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

@Entity
@Table(name = "messages", catalog = "advertisingagency")
public class Message implements java.io.Serializable {

    private Integer messageId;
    private User user;
    private String content;
    private String senderEmail;
    private String senderName;
    private String subject;
    private String senderPhone;

    public Message() {
    }

    public Message(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Message(User user, String content, String senderEmail, String senderName, String subject, String senderPhone) {
        this.user = user;
        this.content = content;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.subject = subject;
        this.senderPhone = senderPhone;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "messageId", unique = true, nullable = false)
    public Integer getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "repliedByUser")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "content", nullable = false, length = 16777215)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "senderEmail", nullable = false, length = 45)
    public String getSenderEmail() {
        return this.senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }
    
    @Column(name = "senderName", nullable = false, length = 45)
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Column(name = "subject", nullable = false, length = 100)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    @Column(name = "senderPhone", length = 45)
    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "adagency.entity.Message[ id=" + messageId + " ]";
    }

}
