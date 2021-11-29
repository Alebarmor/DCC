package acme.testing.anonymous.task;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class AnonymousTaskListAndShowTest extends AcmeOneTest{
	
	// Lifecycle management ---------------------------------------

	// Test cases  ------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/task/list-and-show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listAndShowTest(final int recordIndex, final String title, final String startTime, final String endTime, final String workload, final String description, final String info, final String isPublic, final String manager) {
		super.clickOnMenu("Anonymous", "Task list");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startTime);
		super.checkColumnHasValue(recordIndex, 2, endTime);
		super.checkColumnHasValue(recordIndex, 3, workload);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("endTime", endTime);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("info", info);

	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/task/list-and-show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void listAndShowNegativeTest() {
		super.driver.get("http://localhost:8080/Acme-One/anonymous/task/show?id=41");
		
		super.checkErrorsExist();

	}
}
