package acme.features.anonymous.duty;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class AnonymousDutyShowService implements AbstractShowService<Anonymous, Duty>{

	@Autowired
	protected AnonymousDutyRespository repository;
	
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		
		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findDutyById(id);
		
		final Date now = new Date(System.currentTimeMillis());

		return result.getIsPublic() && result.getEndTime().after(now);
	}
	
	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startTime", "endTime", "workload", "description", "info");		
	}
	
	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;

		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findDutyById(id);

		return result;
	}
}
