package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.PersonDto;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.PersonMapper;
import aqlaam.version2.model.actors.Person;
import aqlaam.version2.repo.PersonRepository;
import aqlaam.version2.service.interfaces.IPersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static aqlaam.version2.service.implemntation.UserService.USER_NOT_FOUND;
import static aqlaam.version2.service.implemntation.UserService.logger;

@Service
@RequiredArgsConstructor
public class PersonService implements IPersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonDto loadUserByEmail(String email) {
        logger.info("Fetching user with user email: {}", email);
        Optional<Person> optionalUser = personRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            logger.error("User not found with email: {}", email);
            throw new CustomNotFoundException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return personMapper.toDto(optionalUser.get());

    }

    @Override
    public PersonDto findPersonById(Long id) {
        logger.info("Fetching user with id: {}", id);
        Optional<Person> optionalUser = personRepository.findPersonById(id);
        if (optionalUser.isEmpty()) {
            logger.error("User not found with id: {}", id);
            throw new CustomNotFoundException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return personMapper.toDto(optionalUser.get());
    }

    @Override
    public List<PersonDto> getAll() {
        List<Person> personList = personRepository.findAll();
        return personList.stream().map(personMapper::toDto).toList();
    }
}
