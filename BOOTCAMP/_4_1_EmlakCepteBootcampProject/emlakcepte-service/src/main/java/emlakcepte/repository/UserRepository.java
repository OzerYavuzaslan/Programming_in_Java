package emlakcepte.repository;

import emlakcepte.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	// select * from Users where email = ?
	// @Query(value = "sql",nativeQuery = true) native sql scripti yazmanız gerekirse
	User findByEmail(String email);

}
