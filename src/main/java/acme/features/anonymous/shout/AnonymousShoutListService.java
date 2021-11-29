/*
 * AnonymousShoutListService.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.shouts.Shout;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractListService;

@Service
public class AnonymousShoutListService implements AbstractListService<Anonymous, Shout> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnonymousShoutRepository repository;


	// AbstractListService<Administrator, Shout> interface --------------

	@Override
	public boolean authorise(final Request<Shout> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Shout> request, final Shout entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "author", "text", "moment", "title");
	}

	@Override
	public Collection<Shout> findMany(final Request<Shout> request) {
		assert request != null;

		Iterator<Shout> auxiliar;
		auxiliar = this.repository.findMany().iterator();
		
		final Collection <Shout> result = new ArrayList<>();
		
		while (auxiliar.hasNext()) {
			final Shout shout = auxiliar.next();
			
			final Date moment = shout.getMoment();
			final Date now = new Date();
			final Date lastMonth = DateUtils.addMonths(now, -1);
			
			if (moment.after(lastMonth) && moment.before(now)) {
				result.add(shout);
			}
		}
		return result;
	}

}
