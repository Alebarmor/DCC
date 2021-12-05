
 /*
+ * Dashboard.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.forms;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Double						averageNumberOfJobsPerEmployer;
	Double						averageNumberOfApplicationsPerWorker;
	Double						averageNumberOfApplicationsPerEmployer;
	Double						ratioOfPendingApplications;
	Double						ratioOfAcceptedApplications;
	Double						ratioOfRejectedApplications;
	
	Integer						numberOfDutiesPublic;
	Integer						numberOfDutiesPrivate;
	Integer						numberOfDutiesFinished;
	Integer						numberOfDutiesUnfinished;
	
	Double						averageWorkload;
	Double						deviationWorkload;
	Double						maximumWorkload;
	Double						minimumWorkload;
	
	Double						averageExecutionPeriod;
	Double						deviationExecutionPeriod;
	Double						maximumExecutionPeriod;
	Double						minimumExecutionPeriod;
	
	Integer 					numberOfEndeavours;
	Integer						numberOfEndeavoursPublic;
	Integer						numberOfEndeavoursPrivate;
	Integer						numberOfEndeavoursFinished;
	Integer						numberOfEndeavoursUnfinished;
	
	Double						averageEndeavoursWorkload;
	Double						deviationEndeavoursWorkload;
	Double						maximumEndeavoursWorkload;
	Double						minimumEndeavoursWorkload;
	
	Double						averageEndeavoursExecutionPeriod;
	Double						deviationEndeavoursExecutionPeriod;
	Double						maximumEndeavoursExecutionPeriod;
	Double						minimumEndeavoursExecutionPeriod;
	
	// Xx1

	Double 						ratioShoutMarked;
	Double						ratioShoutZeroXx4;
	Double 						averageByDollar;
	Double 						averageByEuro;
	Double 						averageByPound;
	Double 						deviationByDollar;
	Double 						deviationByEuro;
	Double 						deviationByPound;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}