package at.ande.vogella.springboot.user.tutorial.controller;

import java.time.Instant;
import java.util.Collections;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.ande.vogella.springboot.user.tutorial.domain.User;
import reactor.core.publisher.Flux;

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
		User user = new User(1, "Fabian Pfaff", "favian.pfaff@vogella.com", "blabla", Collections.singletonList("ADMIN"), Instant.now(), true);
		User user2 = new User(2, "Simon Scholz", "simon.scholz@vogella.com", "sdguidsdsghuds", Collections.singletonList("ADMIN"), Instant.now(), false);
		User user3 = new User(3, "Lars Vogel", "lars.vogel@vogella.com", "sdguidsdsghuds", Collections.singletonList("USER"), Instant.now(), true);

		return Flux.just(user, user2, user3);
	}

	@GetMapping
	public Flux<User> getUsers() {
		return users;
	}

}
