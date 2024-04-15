package aqlaam.version2.repo;

import aqlaam.version2.model.actors.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);
    Optional<User> findUserByEmailAndDeletedFalse(String email);
    Optional<User> findUserByDeletedIsFalseAndId(Long id);
    List<User> findAllByDeletedIsFalse();

}


