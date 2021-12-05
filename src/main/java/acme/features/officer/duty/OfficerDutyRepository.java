package acme.features.officer.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface OfficerDutyRepository extends AbstractRepository{

	@Query("select t from Duty t")
	Collection<Duty> findMany();
	
	@Query("select t from Duty t where t.officer.id = ?1")
	Collection<Duty> findManyDutyByOfficer(int id);
	
	@Query("select m from Officer m where m.id = ?1")
	Officer findOneOfficerById(int id);

	@Query("select t from Duty t where t.id = ?1")
	Duty findOneDutyById(int id);
	
	@Query("select c from Configuration c")
	Collection<Configuration> getConfiguration();
	
}
