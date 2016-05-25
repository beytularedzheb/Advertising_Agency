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
    private Boolean replied;

    public Message() {
    }

    public Message(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Message(User user, String content, String senderEmail, Boolean replied) {
        this.user = user;
        this.content = content;
        this.senderEmail = senderEmail;
        this.replied = replied;
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

    @Column(name = "content", length = 16777215)
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

    @Column(name = "replied")
    public Boolean getReplied() {
        return this.replied;
    }

    public void setReplied(Boolean replied) {
        this.replied = replied;
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
