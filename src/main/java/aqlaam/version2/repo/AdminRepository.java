package aqlaam.version2.repo;

import aqlaam.version2.model.actors.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

  /*  Optional<Admin> findById(Long id);
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByEmail(String email);*/


}


