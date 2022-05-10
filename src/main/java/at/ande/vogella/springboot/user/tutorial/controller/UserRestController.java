package at.ande.vogella.springboot.user.tutorial.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import at.ande.vogella.springboot.user.tutorial.domain.User;
import at.ande.vogella.springboot.user.tutorial.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping
	public Flux<User> getUsers(@RequestParam(name = "limit", required = false, defaultValue = "-1") long limit) {
		return userService.getUsers(limit);
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<User>> findUserById(@PathVariable("id") long id) {
		return userService.findUserById(id).map(ResponseEntity::ok).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

	@PostMapping
	public Mono<ResponseEntity<Object>> newUser(@RequestBody User user, ServerHttpRequest req) {
		return userService.newUser(user).map(u -> ResponseEntity.created(URI.create(req.getPath() + "/" + u.getId())).build());
	}

	@DeleteMapping("/{id}")
	public Mono<Void> deleteUser(@PathVariable("id") int id) {
		return userService.deleteUser(id);
	}

	@GetMapping("/search/{email}")
	/**
	 * @param email
	 * @return
	 *
	 * @see <a href=
	 *      "https://www.vogella.com/tutorials/SpringBoot/article.html#exercise-implement-custom-query-methods">27.
	 *      Exercise: Implement custom query methods</a>
	 *      
	 * @since (May 2022)
	 */
	public Flux<User> search(@PathVariable("email") String email) {
		return userService.getByEmail(email);
	}
	
	@PostMapping("/search")
	public Mono<User> search(@RequestBody User user){
		return userService.findUserbyExample(user);
		
	}
}