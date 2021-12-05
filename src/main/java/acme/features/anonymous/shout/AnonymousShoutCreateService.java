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
import acme.entities.xx1s.Xx1;
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
	
	Xx1 xx1Entity = new Xx1();

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
		
		final boolean isDuplicated = this.repository.findOneXx1ByXx2(this.xx1Entity.getXx2()) != null;
		
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
		
//		if (!errors.hasErrors("xx2")) {
//			errors.state(request, !this.xx1Entity.getXx2().equals(""), "xx2", "anonymous.xx1.form.error.xx2-blank");
//		}
		
//		if (!errors.hasErrors("xx1.xx2")) {
//			errors.state(request, pattern.matcher(this.xx1Entity.getXx2()).matches(), "xx1.xx2", "anonymous.xx1.form.error.xx2-regex");
//		}
		
		if (!errors.hasErrors("xx1.xx2")) {
			errors.state(request, !isDuplicated, "xx1.xx2", "anonymous.xx1.form.error.xx2-duplicated");
		}
		
		if (!errors.hasErrors("xx1.xx2")) {
			errors.state(request, entity.getXx1().isXx2Current(), "xx1.xx2", "anonymous.xx1.form.error.xx2-date");
		}
		
//		if (!errors.hasErrors("xx3")) {
//			errors.state(request, this.xx1Entity.getXx3() != null, "xx3", "anonymous.xx1.form.error.xx3-null");
//		}
		
		if (!errors.hasErrors("xx1.xx3")) {
			errors.state(request, entity.getXx1().getXx3().after(aWeekAfter), "xx1.xx3", "anonymous.xx1.form.error.xx3-week");
		}
		
//		if (!errors.hasErrors("xx4")) {
//			errors.state(request, this.xx1Entity.getXx4() != null, "xx4", "anonymous.xx1.form.error.xx4-null");
//		}
		
		if (!errors.hasErrors("xx1.xx4")) {
			errors.state(request, ((entity.getXx1().getXx4().getCurrency().equals("USD")) || (entity.getXx1().getXx4().getCurrency().equals("EUR")) || (entity.getXx1().getXx4().getCurrency().equals("GBP"))), "xx1.xx4", "anonymous.xx1.form.error.xx4-currency");
		}
		
		if (!errors.hasErrors("xx1.xx4")) {
			errors.state(request, entity.getXx1().getXx4().getAmount() >= 0, "xx1.xx4", "anonymous.xx1.form.error.xx4-positive");
		}
	
	}


	@Override
	public void create(final Request<Shout> request, final Shout entity) {
		assert request != null;
		assert entity != null;

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		
		this.repository.save(entity.getXx1());
		entity.setXx1(entity.getXx1());
		
		this.repository.save(entity);

		this.xx1Entity.setShout(entity);
		this.repository.save(entity.getXx1());
	}

}
