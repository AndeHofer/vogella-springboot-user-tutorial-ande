package at.ande.vogella.springboot.user.tutorial.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import at.ande.vogella.springboot.user.tutorial.data.UserRepository;
import at.ande.vogella.springboot.user.tutorial.domain.User;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * 
 * @author vhhsa02
 * @since (April 2022)
 */
@DataMongoTest
public class UserMongoIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void save_validUserInput_canFoundWithFindAll() throws Exception {
		userRepository.save(User.builder().id(1).name("Lars Vogel").build()).mergeWith(userRepository.save(User.builder().id(2).name("Simon Scholz").build()))
				.blockLast();

		Flux<User> users = userRepository.findAll();

		StepVerifier.create(users).recordWith(ArrayList::new).expectNextCount(2).consumeRecordedWith(userList -> {
			assertThat(userList).withFailMessage("Should contain user with name <%s>", "Simon Scholz").anyMatch(user -> user.getName().equals("Simon Scholz"));
		}).expectComplete().verify();
	}

}
