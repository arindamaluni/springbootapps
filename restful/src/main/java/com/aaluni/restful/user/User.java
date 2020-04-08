package com.aaluni.restful.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User model for the Awesome API")
@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	@Size(min = 2, message = "Size should be at least 2 characters")
	@NotNull (message = "Name can't be null")
	@ApiModelProperty(required = true, notes = "Length should be at least 2 characters")
	private String name;
	@Past
	private Date birthDate;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> posts;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	public User(Integer id, String name, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	
}
