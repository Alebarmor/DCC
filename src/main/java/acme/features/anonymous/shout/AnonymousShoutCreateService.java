/*
 * AnonymousShoutCreateService.java
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
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.shouts.Shout;
import acme.entities.tolems.Tolem;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousShoutCreateService implements AbstractCreateService<Anonymous, Shout> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnonymousShoutRepository repository;

	// AbstractCreateService<Administrator, Shout> interface --------------
	
	Tolem tolemEntity = new Tolem();

	@Override
	public boolean authorise(final Request<Shout> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Shout> request, final Shout entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Shout> request, final Shout entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "author", "text", "info");
	}

	@Override
	public Shout instantiate(final Request<Shout> request) {
		assert request != null;

		Shout result;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new Shout();
		result.setMoment(moment);


		return result;
	}

	@Override
	public void validate(final Request<Shout> request, final Shout entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final List<Configuration> listConfigurations = new ArrayList<>(this.repository.getConfiguration());
		
		final Configuration confEng = listConfigurations.get(0);
		final Configuration confEsp = listConfigurations.get(1);
		
		final boolean isDuplicated = this.repository.findOneTolemByName(entity.getTolem().getName()) != null;
		
		final Date aWeekAfter = new Date(System.currentTimeMillis() + 604800001);

		if (!errors.hasErrors("author")) {
			errors.state(request, !(confEng.spamValidation(entity.getAuthor()) || confEsp.spamValidation(entity.getAuthor())), "author", "anonymous.shout.form.error.spam");
		}
		
		if (!errors.hasErrors("text")) {
			errors.state(request, !(confEng.spamValidation(entity.getText()) || confEsp.spamValidation(entity.getText())), "text", "anonymous.shout.form.error.spam");
		}
		
		if (!errors.hasErrors("info")) {
			errors.state(request, !entity.getInfo().equals(""), "info", "anonymous.shout.form.error.info-blank");
		}
		
		if (!errors.hasErrors("tolem.name")) {
			errors.state(request, !isDuplicated, "tolem.name", "anonymous.tolem.form.error.name-duplicated");
		}
		
		if (!errors.hasErrors("tolem.name")) {
			errors.state(request, entity.getTolem().isNameCurrent(), "tolem.name", "anonymous.tolem.form.error.name-date");
		}
		
		if (!errors.hasErrors("tolem.deadline")) {
			errors.state(request, entity.getTolem().getDeadline().after(aWeekAfter), "tolem.deadline", "anonymous.tolem.form.error.deadline-week");
		}
		
		if (!errors.hasErrors("tolem.budget")) {
			errors.state(request, ((entity.getTolem().getBudget().getCurrency().equals("EUR")) || (entity.getTolem().getBudget().getCurrency().equals("USD")) || (entity.getTolem().getBudget().getCurrency().equals("GBP"))), "tolem.budget", "anonymous.tolem.form.error.budget-currency");
		}
	
	}


	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		assert request != null;
		assert entity != null;

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		
		this.repository.save(entity.getTolem());
		entity.setTolem(entity.getTolem());
		
		this.repository.save(entity);

		this.tolemEntity.setShout(entity);
		this.repository.save(entity.getTolem());
	}

}
