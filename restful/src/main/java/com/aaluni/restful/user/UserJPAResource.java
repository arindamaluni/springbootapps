package com.aaluni.restful.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {
	

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	
	@GetMapping(path = "/jpa/users")
	public List<User> getUsers() {
		return userRepository.findAll();
		
	}

	
	@GetMapping(path="/jpa/users/{id}")
	public Resource getUser(@PathVariable (name = "id")int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) throw new UserNotFoundException(String.format("Id: %s does not exist", id));
		
		//Add HATEOAS links		
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping(path = "/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		
		User savedUser = userRepository.save(user);
		
		URI resourceLink =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
		return ResponseEntity.created(resourceLink).build();
	}
	
	@DeleteMapping(path="/jpa/users/{id}")
	public void deleteUser(@PathVariable (name = "id")int id) {
		
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UserNotFoundException(String.format("Id: %s does not exist", id));
		}
		
		
	}
	
	@GetMapping(path="/jpa/users/{id}/posts")
	public List<Post> getAllPostsForUser(@PathVariable (name = "id")int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) throw new UserNotFoundException(String.format("Id: %s does not exist", id));
		
		return user.get().getPosts();
	}
	
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> addNewPost(@PathVariable int id, @Valid @RequestBody Post post) {
		
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) throw new UserNotFoundException(String.format("Id: %s does not exist", id));
		
		User savedUser = user.get();
		post.setUser(savedUser);
		//This saves the user but not possible to get the id of the new post
//		savedUser.getPosts().add(post);
//		savedUser = userRepository.save(savedUser);

		//Use Post Repository instead
		postRepository.save(post);
		URI resourceLink =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(post.getId())
			.toUri();
		return ResponseEntity.created(resourceLink).build();
	}
}
