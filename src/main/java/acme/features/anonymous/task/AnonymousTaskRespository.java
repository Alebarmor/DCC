package acme.features.anonymous.task;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousTaskRespository extends AbstractRepository {

	
	@Query("select t from Task t")
	Collection<Task> findMany();
	
	@Query("select t from Task t where t.isPublic = true and t.endTime > CURRENT_TIMESTAMP")
	Collection<Task> findPublicNonFinishedTasks();
	
	@Query("select t from Task t where t.id = ?1")
	Task findTaskById(int id);
}
