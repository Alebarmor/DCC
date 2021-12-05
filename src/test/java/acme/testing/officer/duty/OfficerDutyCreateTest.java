package acme.testing.officer.duty;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import acme.testing.AcmeOneTest;
public class OfficerDutyCreateTest extends AcmeOneTest{
	// Lifecycle management -----------------

	// Test Cases ---------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/createPositive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositiveIsPublic(final int recordIndex, final String title, final String startTime, final String endTime, final String workload,
									final String description, final String info) {
		this.navigateHome();
		super.signIn("officer1", "officer1");

		super.clickOnMenu("Officer", "Create duty");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("isPublic", "true");

		super.clickOnSubmitButton("Create");

		super.clickOnMenu("Officer", "List all my duties");

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

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/createPositive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createPositiveIsPrivate(final int recordIndex, final String title, final String startTime, final String endTime, final String workload,
									final String description, final String info) {
		this.navigateHome();
		super.signIn("officer1", "officer1");

		super.clickOnMenu("Officer", "Create duty");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmitButton("Create");

		super.clickOnMenu("Officer", "List all my duties");

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

	@ParameterizedTest
	@CsvFileSource(resources = "/officer/duty/createNegative.csv", encoding = "utf-8", numLinesToSkip = 1)

    
          
            
    

          
    
    
  
	@Order(30)
	public void createNegative(final int recordIndex, final String title, final String startTime, final String endTime, final String workload,
									final String description, final String info) {
		
		this.navigateHome();
		super.signIn("officer1", "officer1");
		
		super.clickOnMenu("Officer", "Create duty");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endTime", endTime);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("isPublic", "true");
		
		super.clickOnSubmitButton("Create");
		
		super.checkErrorsExist();
		super.signOut();
	}
}