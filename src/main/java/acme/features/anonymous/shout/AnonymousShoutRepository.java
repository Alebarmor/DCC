/*
 * AnonymousShoutRepository.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.anonymous.shout;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.entities.shouts.Shout;
import acme.entities.xx1s.Xx1;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousShoutRepository extends AbstractRepository {

	@Query("select s from Shout s")
	Collection<Shout> findMany();
	
	@Query("select s.xx1 from Shout s where s.xx1.xx2 = ?1")
	Xx1 findOneXx1ByXx2(String id);
	
	@Query("select c from Configuration c")
	Collection<Configuration> getConfiguration();

}
