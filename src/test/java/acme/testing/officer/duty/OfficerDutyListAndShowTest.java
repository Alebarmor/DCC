package acme.testing.officer.duty;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class OfficerDutyListAndShowTest extends AcmeOneTest{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listAll(final int recordIndex, final String title, final String startTime, final String endTime, final String workload,
		final String description, final String info) {
		
		super.signIn("officer1", "officer1");
		
		super.clickOnMenu("Officer","List all my duties");
		
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
