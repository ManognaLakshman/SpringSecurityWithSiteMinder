package com.ndg.springdemo.department;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ndg.springdemo.common.ApplicationRepository;
import com.ndg.springdemo.common.BadRequestException;
import com.ndg.springdemo.common.GenericController;
import com.ndg.springdemo.model.Department;

@CrossOrigin()
@RestController
@RequestMapping(DepartmentController.URI)
public class DepartmentController extends GenericController<Department, Integer> {

	@Autowired
	private ProjectionFactory factory;

	static final String URI = "/department";

	// Calling generic controller by passing department repository
	@Autowired
	public DepartmentController(ApplicationRepository<Department, Integer> repo) {
		super(URI, repo);
	}

	@RequestMapping
	public ResponseEntity<Page<DepartmentRecord>> all(Pageable pageable,
			@RequestParam(value = "search", required = false) String search) throws BadRequestException {

		Page<Department> x = super.allImpl(pageable, search);

		Page<DepartmentRecord> projected = x.map(l -> factory.createProjection(DepartmentRecord.class, l));

		return super.listResponse(projected, pageable, search);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	protected ResponseEntity<DepartmentRecord> get(@PathVariable Integer id) {
		Optional<Department> x = super.getImpl(id);
		Optional<DepartmentRecord> projected =  x.map(l->factory.createProjection(DepartmentRecord.class, l));
		return super.respondToGet(id, projected);
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ResponseEntity<Department> create(@RequestBody Department department) throws URISyntaxException {
		if (department.getDeptid() != Department.DEFAULT_DEPT_ID) {
			return super.cannotCreateEntityWithKey();
		}
		Department result = super.createImpl(department);
		return super.created(result.getDeptid(), result);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Department> update(@PathVariable int id, @RequestBody Department department) {
		return super.getImpl(id).map(oldDepartment -> {
			department.setDeptid(id);
			Department updatedEntity = super.updateImpl(oldDepartment, department, false);			
			return super.updated(id, updatedEntity);
		}).orElse(super.updateNotFound(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH )
	public @ResponseBody ResponseEntity<Department> patch(@PathVariable int id,@RequestBody Department entity) {
		return super.getImpl(id).map(oldDepartment -> {
			entity.setDeptid(id);
			Department updatedEntity = super.updateImpl(oldDepartment,entity, true);			
			return super.updated(id, updatedEntity);
			
		}).orElse(super.updateNotFound(id));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	protected ResponseEntity<Department> delete(@PathVariable Integer id) {
		super.deleteImpl(id);
		return super.noContent(id);

	}
}
