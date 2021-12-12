/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.xx1s.Xx1;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}
	
	private double getAverage(final double original) {
		
		if(Double.isNaN(original)) {
			return 0.0;
		}
		
		final BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(original));
		
		final int intValue2 = bigDecimal2.intValue();
		final double decimalPart2 = bigDecimal2.subtract(new BigDecimal(intValue2)).doubleValue();
		
		final int decimalInt = (int) (decimalPart2 * 100);
		
		final double decimalFinal = (decimalInt % 60.0) / 100.0;
		
		final int enteraSumaFinal = (int) (decimalInt - decimalFinal*100) / 60;
		
		final double enteraFinal = (double) intValue2 + (double) enteraSumaFinal;
		
		return enteraFinal + decimalFinal;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, //
			"averageNumberOfJobsPerEmployer", "averageNumberOfApplicationsPerWorker", // 
			"averageNumberOfApplicationsPerEmployer", "ratioOfPendingApplications", //
			"ratioOfRejectedApplications", "ratioOfAcceptedApplications", //
			"numberOfDutiesPublic", "numberOfDutiesPrivate", "numberOfDutiesFinished", //
			"numberOfDutiesUnfinished", "averageWorkload", "deviationWorkload", //
			"maximumWorkload", "minimumWorkload", "averageExecutionPeriod", //
			"deviationExecutionPeriod", "maximumExecutionPeriod", "minimumExecutionPeriod", //
			"numberOfEndeavours",
			"numberOfEndeavoursPublic", "numberOfEndeavoursPrivate", "numberOfEndeavoursFinished", 
			"numberOfEndeavoursUnfinished", //
			"averageEndeavoursExecutionPeriod", "deviationEndeavoursExecutionPeriod",
			"maximumEndeavoursExecutionPeriod","minimumEndeavoursExecutionPeriod", //
			"averageEndeavoursWorkload",  "deviationEndeavoursWorkload",
			"maximumEndeavoursWorkload", "minimumEndeavoursWorkload","ratioShoutMarked",
			"averageByXXA","averageByXXB","averageByXXC","deviationByXXA","deviationByXXB",
			"deviationByXXC","ratioShoutZeroXx4");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result;
		Double averageNumberOfApplicationsPerEmployer;
		Double averageNumberOfApplicationsPerWorker;
		Double averageNumberOfJobsPerEmployer;
		Double ratioOfPendingApplications;
		Double ratioOfAcceptedApplications;
		Double ratioOfRejectedApplications;

		averageNumberOfApplicationsPerEmployer = this.repository.averageNumberOfApplicationsPerEmployer();
		averageNumberOfApplicationsPerWorker = this.repository.averageNumberOfApplicationsPerWorker();
		averageNumberOfJobsPerEmployer = this.repository.averageNumberOfJobsPerEmployer();
		ratioOfPendingApplications = this.repository.ratioOfPendingApplications();
		ratioOfAcceptedApplications = this.repository.ratioOfAcceptedApplications();
		ratioOfRejectedApplications = this.repository.ratioOfRejectedApplications();
		
		result = new Dashboard();
		result.setAverageNumberOfApplicationsPerEmployer(averageNumberOfApplicationsPerEmployer);
		result.setAverageNumberOfApplicationsPerWorker(averageNumberOfApplicationsPerWorker);
		result.setAverageNumberOfJobsPerEmployer(averageNumberOfJobsPerEmployer);
		result.setRatioOfPendingApplications(ratioOfPendingApplications);
		result.setRatioOfAcceptedApplications(ratioOfAcceptedApplications);
		result.setRatioOfRejectedApplications(ratioOfRejectedApplications);
		
		// ------------------- Xx1 -----------------------

		final Double ratioShoutMarked;
		final Double ratioShoutZeroXx4;
		final Double averageByXXA;
		final Double averageByXXB;
		final Double averageByXXC;
		final Double deviationByXXA;
		final Double deviationByXXB;
		final Double deviationByXXC;
		
		ratioShoutMarked = (double)(1.0 - this.repository.numberOfXx1sTrue()/this.repository.numberOfXx1s());
		ratioShoutZeroXx4 = (double)this.repository.numberOfXx1sZeroXx4()/this.repository.numberOfXx1s();
		double XXBs=.0;
		int nXXBs=0;
		double XXAs=.0;
		int nXXAs = 0; 
		double XXCs=.0;
		int nXXCs = 0; 
		for(final Xx1 s : this.repository.findXx1s()) {
			if(s.getXx4().getCurrency().equals("XXA")) {
				nXXAs++;
				XXAs=XXAs+s.getXx4().getAmount();
			}
			if(s.getXx4().getCurrency().equals("XXB")) {
				nXXBs++;
				XXBs=XXBs+s.getXx4().getAmount();
			}
			if(s.getXx4().getCurrency().equals("XXC")) {
				nXXCs++;
				XXCs=XXCs+s.getXx4().getAmount();
			}
		}
		averageByXXA = XXAs/nXXAs;
		averageByXXB = XXBs/nXXBs;
		averageByXXC = XXCs/nXXCs;
		
		Double stddevXXA=.0;
		Double stddevXXB=.0;
		Double stddevXXC=.0;
		for(final Xx1 s : this.repository.findXx1s()) {
			if(s.getXx4().getCurrency().equals("XXA")) {
				stddevXXA += Math.pow(s.getXx4().getAmount() - averageByXXA, 2);
			}
			if(s.getXx4().getCurrency().equals("XXB")) {
				stddevXXB += Math.pow(s.getXx4().getAmount() - averageByXXB, 2);
			}
			if(s.getXx4().getCurrency().equals("XXC")) {
				stddevXXC += Math.pow(s.getXx4().getAmount() - averageByXXC, 2);
			}
		}
		
		deviationByXXA=Math.sqrt(stddevXXA/nXXAs);
		deviationByXXB = Math.sqrt(stddevXXB/nXXBs);
		deviationByXXC = Math.sqrt(stddevXXC/nXXCs);
		
		result.setRatioShoutMarked(ratioShoutMarked);
		result.setRatioShoutZeroXx4(ratioShoutZeroXx4);
		result.setAverageByXXA(averageByXXA);
		result.setAverageByXXB(averageByXXB);
		result.setAverageByXXC(averageByXXC);
		result.setDeviationByXXA(deviationByXXA);
		result.setDeviationByXXB(deviationByXXB);
		result.setDeviationByXXC(deviationByXXC);
		
		// ------------------- Duty -----------------------
		
		final Integer numberOfDutiesPublic;
		final Integer numberOfDutiesPrivate;
		final Integer numberOfDutiesFinished;
		final Integer numberOfDutiesUnfinished;
		
		numberOfDutiesPublic = this.repository.numberOfDutiesPublic();
		numberOfDutiesPrivate = this.repository.numberOfDutiesPrivate();
		numberOfDutiesFinished = this.repository.numberOfDutiesFinished();
		numberOfDutiesUnfinished = this.repository.numberOfDutiesUnfinished();
		
		result.setNumberOfDutiesPublic(numberOfDutiesPublic);
		result.setNumberOfDutiesPrivate(numberOfDutiesPrivate);
		result.setNumberOfDutiesFinished(numberOfDutiesFinished);
		result.setNumberOfDutiesUnfinished(numberOfDutiesUnfinished);
		
		// ------------------- Duty Stats -----------------------
		
		final Double averageWorkload;
		final Double deviationWorkload;
		final Double maximumWorkload;
		final Double minimumWorkload;
		
		final List<Double> wl = new ArrayList<Double>();;
		
		for(final Duty t : this.repository.findMany()) {
			wl.add(t.getWorkload());
		}
		
		Double n = 0.0;
		Double stddev = 0.0;
		
		for(final double d : wl) {
			n += d;
		}
		
		averageWorkload = this.getAverage(n/wl.size());
		
		for(final double d : wl) {
			stddev += Math.pow(d - averageWorkload, 2);
		}
		
		deviationWorkload = Math.sqrt(stddev/wl.size());
		final Optional<Double>  minimumWorkloadOp = wl.stream().min(Comparator.naturalOrder());
		final Optional<Double> maximumWorkloadOp = wl.stream().max(Comparator.naturalOrder());
		if(minimumWorkloadOp.isPresent()) {
			minimumWorkload = minimumWorkloadOp.get();
		}else {
			minimumWorkload = 0.0;
		}
		
		if(maximumWorkloadOp.isPresent()) {
			maximumWorkload = maximumWorkloadOp.get();
		}else {
			maximumWorkload = 0.0;
		}
		
		result.setAverageWorkload(averageWorkload);
		result.setDeviationWorkload(deviationWorkload);
		result.setMaximumWorkload(maximumWorkload);
		result.setMinimumWorkload(minimumWorkload);

		// ------------------- Execution Period Stats -----------------------

		final Double averageExecutionPeriod;
		final Double deviationExecutionPeriod;
		final Double maximumExecutionPeriod;
		final Double minimumExecutionPeriod;

		final List<Double> days = new ArrayList<Double>();

		for (final Duty t : this.repository.findMany()) {
			days.add(t.getDays());
		}

		Double i = 0.0;

		for (final double d : days) {
			i += d;
		}

		averageExecutionPeriod = i / days.size();

		for (final double d : days) {
			stddev += Math.pow(d - averageExecutionPeriod, 2);
		}

		deviationExecutionPeriod = Math.sqrt(stddev / days.size());
		final Optional<Double> minimumExecutionPeriodOp = days.stream().min(Comparator.naturalOrder());
		final Optional<Double> maximumExecutionPeriodOp = days.stream().max(Comparator.naturalOrder());

		if(minimumExecutionPeriodOp.isPresent()) {
			minimumExecutionPeriod = minimumExecutionPeriodOp.get();
		}else {
			minimumExecutionPeriod = 0.0;
		}
		
		if(maximumExecutionPeriodOp.isPresent()) {
			maximumExecutionPeriod = maximumExecutionPeriodOp.get();
		}else {
			maximumExecutionPeriod = 0.0;
		}
		
		result.setAverageExecutionPeriod(averageExecutionPeriod);
		result.setDeviationExecutionPeriod(deviationExecutionPeriod);
		result.setMinimumExecutionPeriod(minimumExecutionPeriod);
		result.setMaximumExecutionPeriod(maximumExecutionPeriod);
	
		return result;
	}
	
	

}
