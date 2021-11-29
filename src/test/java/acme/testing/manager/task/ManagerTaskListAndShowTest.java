package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class ManagerTaskListAndShowTest extends AcmeOneTest{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listAll(final int recordIndex, final String title, final String startTime, final String endTime, final String workload,
		final String description, final String info) {
		
		super.signIn("manager1", "manager1");
		
		super.clickOnMenu("Manager","List all my tasks");
		
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
		
		super.signOut();
		
	}

}
