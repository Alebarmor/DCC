package acme.testing.authenticated.duty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmeOneTest;

public class AuthenticatedDutyListAndShowTest extends AcmeOneTest{
	
	// Lifecycle management ---------------------------------------

	// Test cases  ------------------------------------------------

	@ParameterizedTest	
	@CsvFileSource(resources = "/authenticated/duty/list-and-show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listAndShowTest(final int recordIndex, final String title){
		super.signIn("officer1", "officer1");
		
		super.clickOnMenu("Officer", "Create duty");
		
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");  
		final Date now = new Date(System.currentTimeMillis() + 70000);
		final String strNow = dateFormat.format(now);
		final Date after = new Date(System.currentTimeMillis() + 130000);
		final String strAfter = dateFormat.format(after);
		
		super.fillInputBoxIn("title", "Trying a little test");
		super.fillInputBoxIn("startTime", strNow);
		super.fillInputBoxIn("endTime", strAfter);
		super.fillInputBoxIn("workload", "0.00");
		super.fillInputBoxIn("description", "This is a short duty that we will create to test the authenticated-duty feature");
		super.fillInputBoxIn("info", "http://example.org");
		super.fillInputBoxIn("isPublic", "true");
		super.clickOnSubmitButton("Create");
		
		super.sleep(130, true);
		
		super.clickOnMenu("Authenticated", "Duty list");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, strNow);
		super.checkColumnHasValue(recordIndex, 2, strAfter);
		super.checkColumnHasValue(recordIndex, 3, "0.00");
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startTime", strNow);
		super.checkInputBoxHasValue("endTime", strAfter);
		super.checkInputBoxHasValue("workload", "0.00");
		super.checkInputBoxHasValue("description", "This is a short duty that we will create to test the authenticated-duty feature");
		super.checkInputBoxHasValue("info", "http://example.org");
		
		super.signOut();
	}
	
	@ParameterizedTest	
	@CsvFileSource(resources = "/authenticated/duty/list-and-show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(30)
	public void listAndShowNegativeTest(){
		super.signIn("officer1", "officer1");
		
		super.driver.get("http://localhost:8080/Acme-One/authenticated/duty/show?id=40");
		
		super.checkErrorsExist();
		
		super.driver.get("http://localhost:8080/Acme-One/authenticated/duty/show?id=41");
		
		super.checkErrorsExist();
		
		super.signOut();
	}
}
