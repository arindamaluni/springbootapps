package com.aaluni.restful.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//Spring 2.2.x conflicts with Swagger
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Validated
@RestController
public class UserResource {
	
	@Autowired
	UserDaoService userDao;
	
	@GetMapping(path = "/users")
	public List<User> getUsers() {
		return userDao.findAll();
		
	}
	//New HATEOAS with Spring boot 2.2.3 conflicts with Swagger
//	@GetMapping(path="/users/{id}")
//	public EntityModel<User> getUser(@PathVariable (name = "id")int id) {
//		User user = userDao.findById(id);
//		if (user==null) throw new UserNotFoundException(String.format("Id: %s does not exist", id));
//		
//		//Add HATEOAS links
//		EntityModel<User> model = new EntityModel<>(user);
//		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUsers());
//		model.add(linkTo.withRel("all-users"));
//		return model;
//	}
	
	@GetMapping(path="/users/{id}")
	public Resource getUser(@PathVariable (name = "id")int id) {
		User user = userDao.findById(id);
		if (user==null) throw new UserNotFoundException(String.format("Id: %s does not exist", id));
		
		//Add HATEOAS links		
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		
		User savedUser = userDao.save(user);
		
		URI resourceLink =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		return ResponseEntity.created(resourceLink).build();
	}
	
	@DeleteMapping(path="/users/{id}")
	public User deleteUser(@PathVariable (name = "id")int id) {
		User user = userDao.deleteUser(id);
		if (user==null) throw new UserNotFoundException(String.format("Id: %s does not exist", id));
		return user;
	}
}
