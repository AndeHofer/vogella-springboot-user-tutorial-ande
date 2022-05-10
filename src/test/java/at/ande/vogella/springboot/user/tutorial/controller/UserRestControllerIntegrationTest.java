package at.ande.vogella.springboot.user.tutorial.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.reactive.function.BodyInserters;

import at.ande.vogella.springboot.user.tutorial.domain.User;

/**
 * 
 * @author ande
 * @since (March 2022)
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRestControllerIntegrationTest {
	@Autowired
	private ApplicationContext context;
	private WebTestClient webTestClient;

	@BeforeEach
	public void setUp() {
		webTestClient = WebTestClient.bindToApplicationContext(context).configureClient().baseUrl("/").build();
	}

	@Test
	void ensureThat_getUserByIdFromIntialDataModel_returnUser() {
		ResponseSpec rs = webTestClient.get().uri("/user/1").exchange();

		rs.expectStatus().isOk().expectBody(User.class).consumeWith(result -> {
			User user = result.getResponseBody();
			assertThat(user).isNotNull();
			assertThat(user.getName()).isEqualTo("Fabian Pfaff");
		});
	}

	@Test
	public void getUserById_invalidId_error() {
		ResponseSpec rs = webTestClient.get().uri("/user/-1").exchange();
		rs.expectStatus().isNotFound();
	}

	@Test
	public void ensureThat_createUser_withValidUserInput_createsUser() {
		var user = new User(4L);
		user.setName("Jonas Hungershausen");
		user.setEmail("jonas.hungershausen@vogella.com");
		ResponseSpec rs = webTestClient.post().uri("/user").body(BodyInserters.fromValue(user)).exchange();

		rs.expectStatus().isCreated().expectHeader().valueMatches("LOCATION", "^/user/\\d+");
	}

}
