package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class ManagerTaskDeleteTest extends AcmeOneTest{

	// Lifecycle management -----------------
	
	// Test Cases ---------------------------
	
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void delete(final int recordIndex) {
		this.navigateHome();
		super.signIn("manager1", "manager1");
		
		super.clickOnMenu("Manager", "List all my tasks");

		super.clickOnListingRecord(recordIndex);

		super.clickOnSubmitButton("Delete");	
		
		super.checkSimplePath("/manager/task/list");
		
		super.signOut();
	}

}
