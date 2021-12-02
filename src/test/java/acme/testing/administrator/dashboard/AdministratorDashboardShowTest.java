package acme.testing.administrator.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmeOneTest;

public class AdministratorDashboardShowTest extends AcmeOneTest{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/dashboard/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void show(final String ratioShoutMarked, final String ratioShoutZeroBudget, 
		final String averageByDollar, final String averageByEuro, final String averageByPound,
		final String deviationByDollar, final String deviationByEuro, final String deviationByPound,
		final String averageNumberOfJobsPerEmployer,final String averageNumberOfApplicationsPerWorker,//
		final String averageNumberOfApplicationsPerEmployer, final String numberOfDutiesPublic,//
		final String numberOfDutiesPrivate, final String numberOfDutiesFinished,//
		final String numberOfDutiesUnfinished, final String averageWorkload, final String deviationWorkload,//
		final String maximumWorkload,final String minimumWorkload, final String averageExecutionPeriod,//
		final String deviationExecutionPeriod, final String maximumExecutionPeriod, final String minimumExecutionPeriod) {
		
		@SuppressWarnings("unchecked")
		final List<String> csvList = new ArrayList<>();
		csvList.add(ratioShoutMarked);
		csvList.add(ratioShoutZeroBudget);
		csvList.add(averageByDollar);
		csvList.add(averageByEuro);
		csvList.add(averageByPound);
		csvList.add(deviationByDollar);
		csvList.add(deviationByEuro);
		csvList.add(deviationByPound);
		csvList.add(averageNumberOfJobsPerEmployer);
		csvList.add(averageNumberOfApplicationsPerWorker);
		csvList.add(averageNumberOfApplicationsPerEmployer);
		csvList.add(numberOfDutiesPublic);
		csvList.add(numberOfDutiesPrivate);
		csvList.add(numberOfDutiesFinished);
		csvList.add(numberOfDutiesUnfinished);
		csvList.add(averageWorkload);
		csvList.add(deviationWorkload);
		csvList.add(maximumWorkload);
		csvList.add(minimumWorkload);
		csvList.add(averageExecutionPeriod);
		csvList.add(deviationExecutionPeriod);
		csvList.add(maximumExecutionPeriod);
		csvList.add(minimumExecutionPeriod);
		
		By path;
		int i = 1;
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator","Dashboard");
		
		for(final String csvValue : csvList) {
			
			path = By.xpath("/html/body/div[2]/div/table/tbody/tr[" + i + "]/td\r\n");
			final String pathValue = super.locateOne(path).getText();
			Assertions.assertEquals(csvValue, pathValue);
			i++;
			
		}
		
		super.signOut();
		
	}

}
