package aqlaam.version2.repo;

import aqlaam.version2.model.actors.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);

    Optional<Person> findPersonById(Long id);
}
