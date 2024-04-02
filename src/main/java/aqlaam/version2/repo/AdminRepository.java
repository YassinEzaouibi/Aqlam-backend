package aqlaam.version2.repo;

import aqlaam.version2.model.actors.Admin;
import aqlaam.version2.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByEmailAndDeletedFalse(String email);
    Optional<Admin> findAdminByEmail(String email);
    Optional<Admin> findAdminByIdAndDeletedFalse(Long id);
    List<Admin> findAllByAccountTypeAndDeletedFalse(AccountType accountType);

}
