package aqlaam.version2.repo;

import aqlaam.version2.model.actors.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByDeletedIsFalseAndId(Long id);
    Optional<Admin> findByEmail(String email);


}


