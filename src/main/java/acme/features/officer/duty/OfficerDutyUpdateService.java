package acme.features.officer.duty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class OfficerDutyUpdateService implements AbstractUpdateService<Officer, Duty>{
	
	@Autowired
	protected OfficerDutyRepository repository;
	
	@Override
	public boolean authorise (final Request<Duty> request) {
		assert request != null;
		
		boolean result;
		int dutyId;
		Duty duty;
		Officer officer;
		Principal principal;
		
		dutyId = request.getModel().getInteger("id");
		duty = this.repository.findOneDutyById(dutyId);
		officer = duty.getOfficer();
		principal = request.getPrincipal();
		result = officer.getUserAccount().getId() == principal.getAccountId();
		
		return result;
	}
	
	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		
		if (!errors.hasErrors("endTime") && entity.getStartTime() != null) {
			errors.state(request, entity.getEndTime().after(entity.getStartTime()), "endTime", "officer.duty.form.error.isBefore");
			
		}
		
		if (!errors.hasErrors("workload") && !errors.hasErrors("endTime") && !errors.hasErrors("startTime") && entity.getStartTime() != null) {
			final Double workload = entity.getWorkload();
			final BigDecimal bigDecimal = new BigDecimal(String.valueOf(workload));
			
			final int intValue = bigDecimal.intValue();
			final double decimalPart = bigDecimal.subtract(new BigDecimal(intValue)).doubleValue();
			final double unityPart = intValue - decimalPart;
			
			errors.state(request, decimalPart < 0.60, "workload", "officer.duty.form.error.workload.decimal");
			errors.state(request, unityPart < 100.0, "workload", "officer.duty.form.error.workload.unity");
			errors.state(request, entity.getWorkload() <= entity.getExecutionPeriodInHours(), "workload", "officer.duty.form.error.workload");			
		}
		
		final List<Configuration> listConfigurations = new ArrayList<>(this.repository.getConfiguration());
		
		final Configuration confEng = listConfigurations.get(0);
		final Configuration confEsp = listConfigurations.get(1);
		
		if (!errors.hasErrors("title")) {
			errors.state(request, !(confEng.spamValidation(entity.getTitle()) || confEsp.spamValidation(entity.getTitle())), "title", "officer.duty.form.error.spam");
		}
		
		if (!errors.hasErrors("description")) {
			errors.state(request, !(confEng.spamValidation(entity.getDescription()) || confEsp.spamValidation(entity.getDescription())), "description", "officer.duty.form.error.spam");
		}
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startTime", "endTime", "description", "info", "isPublic", "workload");
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneDutyById(id);

		return result;
	}

	@Override
	public void update(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
	
	

}
