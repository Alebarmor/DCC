package acme.entities.prates;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.entities.shouts.Shout;
import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Prate extends DomainEntity {
	
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	//XXP
	@Pattern(regexp = "^([0-9]{2}[0-1][0-9])/([0-3][0-9])-\\d{5}$")
	@NotBlank
	protected String				marker;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date					deadline;
	
	@Valid
	@NotNull
	protected Money 				budget;
	
	@NotNull
	protected Boolean 				important;
	
	public void setImportant(final Boolean a) {
		this.important = a;
	}
	
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	
	@Valid
	@OneToOne(optional = false, mappedBy = "prate")
	protected Shout shout;
	
	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.marker);

		return result.toString();
	}
	
	public Boolean isMarkerCurrent() {
		Boolean res = false;
		final String[] trozos = this.marker.split("/");
		
		final String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2,4);
		String currentMonth = String.valueOf(LocalDate.now().getMonth().getValue());
		String currentDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		
		if(currentMonth.length()==1) {
			currentMonth = "0" + currentMonth;
		}
		
		if(currentDay.length()==1) {
			currentDay = "0" + currentDay;
		}
		
		final String day = trozos[1].substring(0,2);
		final String month = trozos[0].substring(2,4);
		final String year = trozos[0].substring(0,2);
		
		res = (year.equals(currentYear))
			&& (month.equals(currentMonth))
			&& (day.equals(currentDay));
		
		return res;
	}

}
