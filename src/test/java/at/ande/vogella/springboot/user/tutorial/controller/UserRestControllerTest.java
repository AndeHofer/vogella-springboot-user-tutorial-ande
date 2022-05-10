package at.ande.vogella.springboot.user.tutorial.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.reactive.function.BodyInserters;

import at.ande.vogella.springboot.user.tutorial.domain.User;
import at.ande.vogella.springboot.user.tutorial.service.UserService;
import reactor.core.publisher.Mono;

/**
 * 
 * @author ande
 * @since (March 2022)
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(UserRestController.class)
class UserRestControllerTest {

	@Autowired
	private ApplicationContext context;
	private WebTestClient webTestClient;

	@MockBean
	private UserService userService;

	@BeforeEach
	public void setUp() {
		webTestClient = WebTestClient.bindToApplicationContext(context).configureClient().baseUrl("/").build();
	}

	@Test
	void ensureThat_getUserByIdFromIntialDataModel_returnUser() {
		int id = 1;
		String name = "Fabian Pfaff";
		when(userService.findUserById(id)).thenReturn(Mono.just(User.builder().name(name).build()));

		ResponseSpec rs = webTestClient.get().uri("/user/1").exchange();

		rs.expectStatus().isOk().expectBody(User.class).consumeWith(result -> {
			User user = result.getResponseBody();
			assertThat(user).isNotNull();
			assertThat(user.getName()).isEqualTo("Fabian Pfaff");
		});
	}

	@Test
	public void ensureThat_getUserById_invalidId_resultsInError() throws Exception {
		long invalidId = -1;
		when(userService.findUserById(invalidId)).thenReturn(Mono.empty());

		ResponseSpec rs = webTestClient.get().uri("/user/" + invalidId).exchange();

		rs.expectStatus().isNotFound();
	}

	@Test
	public void ensureThat_createUser_withValidUserInput_createsUser() {
		long id = 42;
		when(userService.newUser(ArgumentMatchers.any())).thenReturn(Mono.just(User.builder().id(id).build()));

		var user = new User(4L);
		user.setName("Jonas Hungershausen");
		user.setEmail("jonas.hungershausen@vogella.com");
		ResponseSpec rs = webTestClient.post().uri("/user").body(BodyInserters.fromValue(user)).exchange();

		rs.expectStatus().isCreated().expectHeader().valueEquals("LOCATION", "/user/" + id);
	}

}
