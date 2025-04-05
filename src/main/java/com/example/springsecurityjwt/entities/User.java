package com.example.springsecurityjwt.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username"),
				@UniqueConstraint(columnNames = "email")
		})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,
			generator = "user_id_generator")
	@TableGenerator(name = "user_id_generator",
			table = "id_generator",
			pkColumnName = "gen_name",
			valueColumnName = "gen_value",
			pkColumnValue = "user_id",
			initialValue = 1000,
			allocationSize = 50)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
		// Default role CLIENT when a user is created
		this.roles.add(new Role(ERole.ROLE_USER));
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles.add(new Role(ERole.ROLE_USER));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
