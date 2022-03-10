package at.ande.vogella.springboot.user.tutorial.controller;

import java.time.Instant;
import java.util.Collections;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ande.vogella.springboot.user.tutorial.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author ande
 * @since (January 2022)
 */
@RestController
@RequestMapping("/user")
class UserRestController {

	private Flux<User> users;

	public UserRestController() {
		users = createUserModel();
	}

	private Flux<User> createUserModel() {
		User user = new User(1, "Fabian Pfaff der Unendliche", "favian.pfaff@vogella.com", "blabla", Collections.singletonList("ADMIN"), Instant.now(), true);
		User user2 = new User(2, "Simon Scholz", "simon.scholz@vogella.com", "sdguidsdsghuds", Collections.singletonList("ADMIN"), Instant.now(), false);
		User user3 = new User(3, "Lars Vogel", "lars.vogel@vogella.com", "sdguidsdsghuds", Collections.singletonList("USER"), Instant.now(), true);

		return Flux.just(user, user2, user3);
	}

	@GetMapping
	public Flux<User> getUsers(@RequestParam(name = "limit", required = false, defaultValue = "-1") long limit) {
		if (-1 == limit) {
			return users;
		}
		return users.take(limit);
	}

	@GetMapping("/{id}")
	public Mono<User> getUserById(@PathVariable("id") long id) {
		return Mono.from(users.filter(user -> id == user.getId()));
	}

	/**
	 * Exercise 19 is missing an empty constructor in class User, does not work
	 * without.
	 * https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu
	 * 
	 * @param user
	 * @return
	 *
	 * @author ande
	 * @since (March 2022)
	 */
	@PostMapping
	public Mono<User> newUser(@RequestBody User user) {
		Mono<User> userMono = Mono.just(user);
		users = users.mergeWith(userMono); //
		return userMono;
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteUser(@PathVariable("id") int id){
			 users = users.filter(user -> user.getId() != id);
			 return users.then();
	}			 
}
