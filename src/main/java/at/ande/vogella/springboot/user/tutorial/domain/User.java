/**
 * 
 */
package at.ande.vogella.springboot.user.tutorial.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;

/**
 * 
 * @author vhhsa02
 * @since (January 2022)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class User {

	public User() {

	}

	public User(Long id) {
		this.id = id;
	}

	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public User(Long id, String name, String email, String password, List<String> roles, Instant lastLogin, boolean enabled) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.lastLogin = lastLogin;
		this.enabled = enabled;
	}
	@Id
	private Long id;

	@Builder.Default
	private String name = "";

	@Builder.Default
	private String email = "";
	
	@Builder.Default
	private String password = "";

	@Builder.Default
	private List<String> roles = new ArrayList<>();

	@Builder.Default
	private Instant lastLogin = Instant.now();

	private boolean enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
