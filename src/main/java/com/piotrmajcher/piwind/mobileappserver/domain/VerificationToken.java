package com.piotrmajcher.piwind.mobileappserver.domain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class VerificationToken {
	 private static final int EXPIRATION = 20; // MINUTES

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    
	    @Column(unique = true)
	    private String token;

	    private Date expiryDate;

	    private Date calculateExpiryDate(int expiryTimeInMinutes) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(new Timestamp(cal.getTime().getTime()));
	        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	        return new Date(cal.getTime().getTime());
	    }

	    public VerificationToken() {
	        super();
	        expiryDate = calculateExpiryDate(EXPIRATION);
	    }

	    public static int getExpirationTimeMinutes() {
	        return EXPIRATION;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }

	    public Date getExpiryDate() {
	        return expiryDate;
	    }

	    public void setExpiryDate(Date expiryDate) {
	        this.expiryDate = expiryDate;
	    }
}
