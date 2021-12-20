package acme.testing.anonymous.shout;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class AnonymousShoutCreateTest extends AcmeOneTest {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/createNegativeIdentifier.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(30)
	public void createNegativeDate(final int recordIndex, final String author, final String text, final String info,
		final String name, final String deadline, final String budget) {
		
		super.clickOnMenu("Anonymous","Shout!");
		
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("tolem.name", name);
		super.fillInputBoxIn("tolem.deadline", deadline);
		super.fillInputBoxIn("tolem.budget", budget);
		super.fillInputBoxIn("tolem.important", "true");
		
		super.clickOnSubmitButton("Shout!");
		
		super.checkErrorsExist();
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/createNegative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createNegative(final int recordIndex, final String author, final String text, final String info,
		final String deadline, final String budget) {
		
		// ------------------------
		
		final String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2,4);
		String currentMonth = String.valueOf(LocalDate.now().getMonth().getValue());
		String currentDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				
		if(currentMonth.length()==1) {
			currentMonth = "0" + currentMonth;
		}
				
		if(currentDay.length()==1) {
			currentDay = "0" + currentDay;
		}
		
		//XXP
		final String name =  "ww:" + currentYear + ":" + currentMonth +  currentDay;
		
		// ------------------------
		
		super.clickOnMenu("Anonymous","Shout!");
		
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("tolem.name", name);
		super.fillInputBoxIn("tolem.deadline", deadline);
		super.fillInputBoxIn("tolem.budget", budget);
		super.fillInputBoxIn("tolem.important", "true");
		
		super.clickOnSubmitButton("Shout!");
		
		super.checkErrorsExist();
		
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/shout/createPositive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createPositive(final int recordIndex, final String author, final String text, final String info,
		final String deadline, final String budget) {
		
		// ------------------------
		
		final String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2,4);
		String currentMonth = String.valueOf(LocalDate.now().getMonth().getValue());
		String currentDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		
		if(currentMonth.length()==1) {
			currentMonth = "0" + currentMonth;
		}
		
		if(currentDay.length()==1) {
			currentDay = "0" + currentDay;
		}
		
		//XXP
		final String name =  "ww:" + currentYear + ":" + currentMonth +  currentDay;
		
		// ------------------------
		
		super.clickOnMenu("Anonymous","Shout!");
		
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("tolem.name", name);
		super.fillInputBoxIn("tolem.deadline", deadline);
		super.fillInputBoxIn("tolem.budget", budget);
		super.fillInputBoxIn("tolem.important", "true");
		
		super.clickOnSubmitButton("Shout!");
		
		super.clickOnMenu("Anonymous","List shouts");
		
		super.checkColumnHasValue(3, 1, author);
		super.checkColumnHasValue(3, 2, text);
		super.checkColumnHasValue(3, 3, name);
		
	}
	
}
