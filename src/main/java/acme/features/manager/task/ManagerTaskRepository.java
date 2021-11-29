package acme.features.manager.task;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ManagerTaskRepository extends AbstractRepository{

	@Query("select t from Task t")
	Collection<Task> findMany();
	
	@Query("select t from Task t where t.manager.id = ?1")
	Collection<Task> findManyTaskByManager(int id);
	
	@Query("select m from Manager m where m.id = ?1")
	Manager findOneManagerById(int id);

	@Query("select t from Task t where t.id = ?1")
	Task findOneTaskById(int id);
	
	@Query("select c from Configuration c")
	Collection<Configuration> getConfiguration();
	
}
