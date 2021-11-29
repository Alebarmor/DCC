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
import acme.entities.prates.Prate;
import acme.entities.shouts.Shout;
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
	
	Prate prateEntity = new Prate();

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
		
		final boolean isDuplicated = this.repository.findOnePrateByMarker(this.prateEntity.getMarker()) != null;
		
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
		
//		if (!errors.hasErrors("marker")) {
//			errors.state(request, !this.prateEntity.getMarker().equals(""), "marker", "anonymous.prate.form.error.marker-blank");
//		}
		
//		if (!errors.hasErrors("prate.marker")) {
//			errors.state(request, pattern.matcher(this.prateEntity.getMarker()).matches(), "prate.marker", "anonymous.prate.form.error.marker-regex");
//		}
		
		if (!errors.hasErrors("prate.marker")) {
			errors.state(request, !isDuplicated, "prate.marker", "anonymous.prate.form.error.marker-duplicated");
		}
		
		if (!errors.hasErrors("prate.marker")) {
			errors.state(request, entity.getPrate().isMarkerCurrent(), "prate.marker", "anonymous.prate.form.error.marker-date");
		}
		
//		if (!errors.hasErrors("deadline")) {
//			errors.state(request, this.prateEntity.getDeadline() != null, "deadline", "anonymous.prate.form.error.deadline-null");
//		}
		
		if (!errors.hasErrors("prate.deadline")) {
			errors.state(request, entity.getPrate().getDeadline().after(aWeekAfter), "prate.deadline", "anonymous.prate.form.error.deadline-week");
		}
		
//		if (!errors.hasErrors("budget")) {
//			errors.state(request, this.prateEntity.getBudget() != null, "budget", "anonymous.prate.form.error.budget-null");
//		}
		
		if (!errors.hasErrors("prate.budget")) {
			errors.state(request, ((entity.getPrate().getBudget().getCurrency().equals("USD")) || (entity.getPrate().getBudget().getCurrency().equals("EUR")) || (entity.getPrate().getBudget().getCurrency().equals("GBP"))), "prate.budget", "anonymous.prate.form.error.budget-currency");
		}
		
		if (!errors.hasErrors("prate.budget")) {
			errors.state(request, entity.getPrate().getBudget().getAmount() >= 0, "prate.budget", "anonymous.prate.form.error.budget-positive");
		}
	
	}


	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		assert request != null;
		assert entity != null;

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		
		this.repository.save(entity.getPrate());
		entity.setPrate(entity.getPrate());
		
		this.repository.save(entity);

		this.prateEntity.setShout(entity);
		this.repository.save(entity.getPrate());
	}

}
