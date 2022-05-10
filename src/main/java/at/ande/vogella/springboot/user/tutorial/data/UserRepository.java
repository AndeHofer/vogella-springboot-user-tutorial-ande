package at.ande.vogella.springboot.user.tutorial.data;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import at.ande.vogella.springboot.user.tutorial.domain.User;
import reactor.core.publisher.Flux;

/**
 * 
 * @author vhhsa02
 * @since (April 2022)
 */
public interface UserRepository extends ReactiveCrudRepository<User, Long>, ReactiveQueryByExampleExecutor<User>{
	Flux<User> findByEmailContainingIgnoreCase(String email);

}
