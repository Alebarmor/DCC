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
import acme.entities.solims.Solim;
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
			"averageByEUR","averageByUSD","averageByGBP","deviationByEUR","deviationByUSD",
			"deviationByGBP","ratioShoutZeroBudget");
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
		
		// ------------------- Solim -----------------------

		final Double ratioShoutMarked;
		final Double ratioShoutZeroBudget;
		final Double averageByEUR;
		final Double averageByUSD;
		final Double averageByGBP;
		final Double deviationByEUR;
		final Double deviationByUSD;
		final Double deviationByGBP;
		
		ratioShoutMarked = ((double) this.repository.numberOfSolimsTrue())/((double)this.repository.numberOfSolims());
		ratioShoutZeroBudget = (double)this.repository.numberOfSolimsZeroBudget()/this.repository.numberOfSolims();
		double USDs=.0;
		int nUSDs=0;
		double EURs=.0;
		int nEURs = 0; 
		double GBPs=.0;
		int nGBPs = 0; 
		for(final Solim s : this.repository.findSolims()) {
			if(s.getBudget().getCurrency().equals("EUR")) {
				nEURs++;
				EURs=EURs+s.getBudget().getAmount();
			}
			if(s.getBudget().getCurrency().equals("USD")) {
				nUSDs++;
				USDs=USDs+s.getBudget().getAmount();
			}
			if(s.getBudget().getCurrency().equals("GBP")) {
				nGBPs++;
				GBPs=GBPs+s.getBudget().getAmount();
			}
		}
		averageByEUR = EURs/nEURs;
		averageByUSD = USDs/nUSDs;
		averageByGBP = GBPs/nGBPs;
		
		Double stddevEUR=.0;
		Double stddevUSD=.0;
		Double stddevGBP=.0;
		for(final Solim s : this.repository.findSolims()) {
			if(s.getBudget().getCurrency().equals("EUR")) {
				stddevEUR += Math.pow(s.getBudget().getAmount() - averageByEUR, 2);
			}
			if(s.getBudget().getCurrency().equals("USD")) {
				stddevUSD += Math.pow(s.getBudget().getAmount() - averageByUSD, 2);
			}
			if(s.getBudget().getCurrency().equals("GBP")) {
				stddevGBP += Math.pow(s.getBudget().getAmount() - averageByGBP, 2);
			}
		}
		
		deviationByEUR=Math.sqrt(stddevEUR/nEURs);
		deviationByUSD = Math.sqrt(stddevUSD/nUSDs);
		deviationByGBP = Math.sqrt(stddevGBP/nGBPs);
		
		result.setRatioShoutMarked(ratioShoutMarked);
		result.setRatioShoutZeroBudget(ratioShoutZeroBudget);
		result.setAverageByEUR(averageByEUR);
		result.setAverageByUSD(averageByUSD);
		result.setAverageByGBP(averageByGBP);
		result.setDeviationByEUR(deviationByEUR);
		result.setDeviationByUSD(deviationByUSD);
		result.setDeviationByGBP(deviationByGBP);
		
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
