package team3_backend.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team3_backend.backend.models.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByAuthority(String authority);
}

