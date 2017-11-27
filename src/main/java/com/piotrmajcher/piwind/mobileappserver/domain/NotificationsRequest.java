package com.piotrmajcher.piwind.mobileappserver.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id", "meteoStation_id"})
})
public class NotificationsRequest {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
	 
	 @NotNull
	 @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
	 @JoinColumn(nullable = false, name = "user_id")
	 private UserEntity user;
	 
	 @NotNull
	 @ManyToOne(targetEntity = MeteoStation.class, fetch = FetchType.LAZY)
	 @JoinColumn(nullable = false, name = "meteoStation_id")
	 private MeteoStation meteoStation;
	 
	 @NotNull
	 private Integer minWindLimit;

	public String getUsername() {
		return user.getUsername();
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public MeteoStation getMeteoStation() {
		return meteoStation;
	}

	public void setMeteoStation(MeteoStation meteoStation) {
		this.meteoStation = meteoStation;
	}

	public Integer getMinWindLimit() {
		return minWindLimit;
	}

	public void setMinWindLimit(Integer minWindLimit) {
		this.minWindLimit = minWindLimit;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meteoStation == null) ? 0 : meteoStation.hashCode());
		result = prime * result + ((minWindLimit == null) ? 0 : minWindLimit.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationsRequest other = (NotificationsRequest) obj;
		if (meteoStation == null) {
			if (other.meteoStation != null)
				return false;
		} else if (!meteoStation.equals(other.meteoStation))
			return false;
		if (minWindLimit == null) {
			if (other.minWindLimit != null)
				return false;
		} else if (!minWindLimit.equals(other.minWindLimit))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
