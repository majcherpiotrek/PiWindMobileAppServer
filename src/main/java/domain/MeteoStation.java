package domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.constraints.URL;

@Entity
public class MeteoStation {
	
	@Id
	private UUID id;
	
	@URL
	private String stationBaseURL;
}
