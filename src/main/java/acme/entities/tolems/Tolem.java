package acme.entities.tolems;

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
public class Tolem extends DomainEntity {
	
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	//XXP w{2,4}:yy:mmdd
	@Pattern(regexp = "^\\w{2,4}:(\\d{2}):([0-1][0-9][0-3][0-9])$")
	@NotBlank
	protected String				name;
	
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
	@OneToOne(optional = false, mappedBy = "tolem")
	protected Shout shout;
	
	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.name);

		return result.toString();
	}
	
	//XXP
	public Boolean isNameCurrent() {
		Boolean res = false;
		final String[] trozos = this.name.split(":");
		
		final String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2,4);
		String currentMonth = String.valueOf(LocalDate.now().getMonth().getValue());
		String currentDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		
		if(currentMonth.length()==1) {
			currentMonth = "0" + currentMonth;
		}
		
		if(currentDay.length()==1) {
			currentDay = "0" + currentDay;
		}
		
		final String day = trozos[2].substring(2,4);
		final String month = trozos[2].substring(0,2);
		final String year = trozos[1];
		
		res = (year.equals(currentYear))
			&& (month.equals(currentMonth))
			&& (day.equals(currentDay));
		
		return res;
	}

}
