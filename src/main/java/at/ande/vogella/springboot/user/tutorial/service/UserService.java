package at.ande.vogella.springboot.user.tutorial.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import at.ande.vogella.springboot.user.tutorial.data.UserRepository;
import at.ande.vogella.springboot.user.tutorial.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author ande
 * @since (March 2022)
 */
@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		createUserModel();
	}

	private void createUserModel() {
		User user = new User(1, "Fabian Pfaff", "fabian.pfaff@vogella.com", "sdguidsdsghuds", Collections.singletonList("ADMIN"), Instant.now(), true);
		User user2 = new User(2, "Simon Scholz", "simon.scholz@vogella.com", "sdguidsdsghuds", Collections.singletonList("ADMIN"), Instant.now(), false);
		User user3 = new User(3, "Lars Vogel", "lars.vogel@vogella.com", "sdguidsdsghuds", Collections.singletonList("USER"), Instant.now(), true);

		userRepository.saveAll(Arrays.asList(user, user2, user3)).subscribe();
	}

	public Flux<User> getUsers(@RequestParam(name = "limit", required = false, defaultValue = "-1") long limit) {
		if (-1 == limit) {
			return userRepository.findAll();
		}
		return userRepository.findAll().take(limit);
	}

	public Mono<User> findUserById(@PathVariable("id") long id) {
		return userRepository.findById(id);
	}

	public Mono<User> newUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	public Mono<Void> deleteUser(@PathVariable("id") long id) {
		return userRepository.deleteById(id);
	}
	
//	public Flux<User> getByEmail(String email){
//		Flux<User> findAll = userRepository.findAll();
//		Flux<User> filteredFlux = findAll.filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()));
//		return filteredFlux;
//	}

}
