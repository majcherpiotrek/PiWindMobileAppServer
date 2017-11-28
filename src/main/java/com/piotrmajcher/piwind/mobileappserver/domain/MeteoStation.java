package com.piotrmajcher.piwind.mobileappserver.domain;

import java.util.Arrays;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.piotrmajcher.piwind.mobileappserver.enums.WindDirection;

@Entity
public class MeteoStation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@URL(message = "Valid REST API base url of the station must be specified.")
	@NotEmpty(message = "Valid REST API base url of the station must be specified.")
	private String stationBaseURL;
	
	@NotEmpty(message = "A unique name for the meteo station must be specified.")
	@Column(unique = true)
	@Length(min = 3, max = 100, message = "The station's name must be between 3 and 100 characters.")
	private String name;
	
	@Column
	private String description;
	
	@Column
	private WindDirection beachFacingDirection;
	
	@Column
	private String[] bestWindDirections;
	
	public UUID getId() {
		return id;
	}

	public String getStationBaseURL() {
		return stationBaseURL;
	}

	public void setStationBaseURL(String stationBaseURL) {
		this.stationBaseURL = stationBaseURL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public WindDirection getBeachFacingDirection() {
		return beachFacingDirection;
	}

	public void setBeachFacingDirection(WindDirection beachFacingDirection) {
		this.beachFacingDirection = beachFacingDirection;
	}

	public String[] getBestWindDirections() {
		return bestWindDirections;
	}

	public void setBestWindDirections(String[] bestWindDirections) {
		this.bestWindDirections = bestWindDirections;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beachFacingDirection == null) ? 0 : beachFacingDirection.hashCode());
		result = prime * result + Arrays.hashCode(bestWindDirections);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((stationBaseURL == null) ? 0 : stationBaseURL.hashCode());
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
		MeteoStation other = (MeteoStation) obj;
		if (beachFacingDirection != other.beachFacingDirection)
			return false;
		if (!Arrays.equals(bestWindDirections, other.bestWindDirections))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stationBaseURL == null) {
			if (other.stationBaseURL != null)
				return false;
		} else if (!stationBaseURL.equals(other.stationBaseURL))
			return false;
		return true;
	}
}
