package administrator;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class AdministratorConfigurationListTest extends AcmeOneTest {
	
	// Lifecycle management ---------------------------------------
	
	// Test cases  ------------------------------------------------
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/configuration/list-and-show-test.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listAndShowTest(final int recordIndex, final String words, final String threshold, final String language) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Configuration");
		
		super.checkColumnHasValue(recordIndex, 0, language);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("words", words);
		super.checkInputBoxHasValue("threshold", threshold);
		super.checkInputBoxHasValue("language", language);
		
		super.signOut();
	}
	
	// Ancillary methods ------------------------------------------

}
