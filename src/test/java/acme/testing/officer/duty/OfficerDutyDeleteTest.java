package acme.testing.officer.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class OfficerDutyDeleteTest extends AcmeOneTest{

	// Lifecycle management -----------------
	
	// Test Cases ---------------------------
	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void delete(final int recordIndex) {
		this.navigateHome();
		super.signIn("officer1", "officer1");
		
		super.clickOnMenu("Officer", "List all my duties");

		super.clickOnListingRecord(recordIndex);

		super.clickOnSubmitButton("Delete");	
		
		super.checkSimplePath("/officer/duty/list");
		
		super.signOut();
	}

}
