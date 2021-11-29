package acme.entities.configuration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Configuration extends DomainEntity{
	
	private static final long	serialVersionUID	= 1L;
	
	
	protected String			words;
	
	@Range(min = 0, max = 1)
	private double				threshold;

	@NotBlank
	private String				language;
	
	public Boolean spamValidation(final String s) {
        Boolean res = true;
        
        final String[] spamWords = this.words.split(", ");
        
        String sm = s.toLowerCase().replace(",", " ").replace(".", " ").replace(";", " ").replace("  ", " ");
        
        final String[] stringWords = sm.split(" ");
        
        final int n = stringWords.length;
        
        for(int i = 0; i < spamWords.length; ++i) {
            sm = sm.replace(spamWords[i], "");
        }
        
        final String[] stringWordsReduced = sm.replace("  ", " ").split(" ");
        
        final int nr = stringWordsReduced.length;
        
        res = (n - nr) >= (this.threshold * n);
        
        return res;
    }
}
