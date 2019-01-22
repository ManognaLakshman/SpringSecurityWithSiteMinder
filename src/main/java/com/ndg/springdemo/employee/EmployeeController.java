package com.ndg.springdemo.employee;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ndg.springdemo.common.BadRequestException;
import com.ndg.springdemo.common.GenericController;
import com.ndg.springdemo.model.Employee;

@CrossOrigin()
@RequestMapping(EmployeeController.URI)
@RestController
public class EmployeeController extends GenericController<Employee, Integer> {

	static final String URI = "/employee";

	@Autowired
	public EmployeeController(EmployeeRepo repo) {
		super(URI, repo);
	}

	@Autowired
	private ProjectionFactory factory;

	@RequestMapping(value = "/")
	public ResponseEntity<Page<EmployeeRecord>> all(Pageable pageable, @RequestParam(value = "search", required = false) String search) throws BadRequestException {

		Page<Employee> x = super.allImpl(pageable, search);
		Page<EmployeeRecord> projected = x.map(l -> factory.createProjection(EmployeeRecord.class, l));
		return super.listResponse(projected, pageable, search);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	protected ResponseEntity<Employee> get(@PathVariable Integer id) {
		return super.respondToGet(id, super.getImpl(id));
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ResponseEntity<Employee> create(@RequestBody Employee employee) throws URISyntaxException {

		if (employee.getId() != Employee.DEFAULT_ID) {
			return super.cannotCreateEntityWithKey();
		}
		Employee result = super.createImpl(employee);
		return super.created(result.getId(), result);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	protected ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody Employee employee) {
		return super.getImpl(id).map(oldEmployee -> {
			Employee updatedEntity = super.updateImpl(oldEmployee, employee, false);
			return super.updated(id, updatedEntity);
		}).orElse(super.updateNotFound(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	protected ResponseEntity<Employee> delete(@PathVariable Integer id) {
		super.deleteImpl(id);
		return super.noContent(id);
	}
}
