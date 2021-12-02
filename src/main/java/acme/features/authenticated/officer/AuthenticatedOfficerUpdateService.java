
package acme.features.authenticated.officer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Officer;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedOfficerUpdateService implements AbstractUpdateService<Authenticated, Officer> {


	@Autowired
	protected AuthenticatedOfficerRepository repository;


	@Override
	public boolean authorise(final Request<Officer> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Officer> request, final Officer entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Officer> request, final Officer entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model);
	}

	@Override
	public Officer findOne(final Request<Officer> request) {
		assert request != null;

		Officer result;
		Principal principal;
		int userAccountId;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();

		result = this.repository.findOneOfficerByUserAccountId(userAccountId);

		return result;
	}

	@Override
	public void validate(final Request<Officer> request, final Officer entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<Officer> request, final Officer entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Officer> request, final Response<Officer> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}