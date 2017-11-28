package com.piotrmajcher.piwind.mobileappserver.domain;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(unique = true)
	@NotEmpty(message = "Please provide your username")
	@Length(min = 2, max = 50, message = "The username must be between 2 and 20 characters")
	private String username;

	@Column(unique = true)
	@Email(message = "Please provide a valid email")
	@NotEmpty(message = "Please provide an email")
	private String email;

    @NotEmpty(message = "Please provide a password")
	@Length(min = 8, message = "The password must be at least 8 characters")
	private String password;
    
    @OneToOne(targetEntity = VerificationToken.class, cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = true, name = "token_id")
    private VerificationToken token;
    

    private boolean enabled;

    public UserEntity() {
    	super();
    	this.enabled = false;

	}
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public VerificationToken getToken() {
		return token;
	}
	
	public void setToken(VerificationToken token) {
		this.token = token;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;

        UserEntity that = (UserEntity) o;

        if (!getUsername().equals(that.getUsername())) return false;
        if (!getEmail().equals(that.getEmail())) return false;
        return getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}
