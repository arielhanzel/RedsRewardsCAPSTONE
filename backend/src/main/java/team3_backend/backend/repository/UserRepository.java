package team3_backend.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team3_backend.backend.models.ApplicationUser;


@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
	Optional<ApplicationUser> findByUsername(String username);
	Optional<ApplicationUser> findByUsernameAndEmail(String username, String email);
}
