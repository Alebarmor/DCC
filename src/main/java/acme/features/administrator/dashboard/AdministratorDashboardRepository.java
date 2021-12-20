/*
 * AdministratorDashboardRepository.java
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

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;
import acme.entities.tolems.Tolem;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select avg(select count(j) from Job j where j.employer.id = e.id) from Employer e")
	Double averageNumberOfJobsPerEmployer();

	@Query("select avg(select count(a) from Application a where a.worker.id = w.id) from Worker w")
	Double averageNumberOfApplicationsPerWorker();

	@Query("select avg(select count(a) from Application a where exists(select j from Job j where j.employer.id = e.id and a.job.id = j.id)) from Employer e")
	Double averageNumberOfApplicationsPerEmployer();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.PENDING")
	Double ratioOfPendingApplications();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.ACCEPTED")
	Double ratioOfAcceptedApplications();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.REJECTED")
	Double ratioOfRejectedApplications();
	
	//-------------------------------------------------- Tolem -------------------------------------------------------------------------
	
	@Query("select count(s) from Tolem s")
	Integer numberOfTolems();
	
	@Query("select count(s) from Tolem s where s.important = true")
	Integer numberOfTolemsTrue();
	
	@Query("select count(s) from Tolem s where s.budget.amount = 0.0")
	Integer numberOfTolemsZeroBudget();
	
	@Query("select s from Tolem s")
	Collection<Tolem> findTolems();
			
	// ------------------------------------------------- Duty --------------------------------------------------------------------------
	
	@Query("select count(t) from Duty t where t.isPublic = true")
	Integer numberOfDutiesPublic();
		
	@Query("select count(t) from Duty t where t.isPublic = false")
	Integer numberOfDutiesPrivate();
		
	@Query("select count(t) from Duty t where t.endTime < CURRENT_TIMESTAMP")
	Integer numberOfDutiesFinished();
		
	@Query("select count(t) from Duty t where t.endTime > CURRENT_TIMESTAMP")
	Integer numberOfDutiesUnfinished();
		
	@Query("select t from Duty t")
	Collection<Duty> findMany();

}
