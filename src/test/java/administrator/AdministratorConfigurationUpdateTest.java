package administrator;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class AdministratorConfigurationUpdateTest extends AcmeOneTest {
	
	// Lifecycle management ---------------------------------------
	
	// Test cases  ------------------------------------------------
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/configuration/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositiveTests(final int recordIndex, final String words, final String threshold, final String language) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Configuration");
		
		super.clickOnListingRecord(recordIndex); 
		
		super.fillInputBoxIn("words", words);
		super.fillInputBoxIn("threshold", threshold);
		super.clickOnSubmitButton("Update"); 
		
//		super.checkSimplePath("administrator/configuration/list");

		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("words", words);
		super.checkInputBoxHasValue("threshold", threshold);
		super.checkInputBoxHasValue("language", language);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/configuration/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegativeTests(final int recordIndex, final String words, final String threshold, final String language) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Configuration");
		
		super.clickOnListingRecord(recordIndex); 
		
		super.fillInputBoxIn("words", words);
		super.fillInputBoxIn("threshold", threshold);
		super.clickOnSubmitButton("Update"); 
		
		super.checkErrorsExist();
		
		super.signOut();
	}
}
