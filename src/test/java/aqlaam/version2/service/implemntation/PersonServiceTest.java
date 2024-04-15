package aqlaam.version2.service.implemntation;

import aqlaam.version2.dto.PersonDto;
import aqlaam.version2.exception.CustomNotFoundException;
import aqlaam.version2.mapper.PersonMapper;
import aqlaam.version2.model.actors.Person;
import aqlaam.version2.model.enums.AccountType;
import aqlaam.version2.model.enums.Sex;
import aqlaam.version2.repo.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonServiceTest {

    private PersonRepository personRepository;
    private PersonMapper personMapper;
    private PersonService personService;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        personMapper = mock(PersonMapper.class);
        personService = new PersonService(personRepository, personMapper);
    }

    @Test
    void shouldReturnPersonDtoWhenEmailExists() {
        // Given
        String email = "test@example.com";
        Person person = new Person();
        PersonDto personDto = PersonDto.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email(email)
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .accountType(AccountType.USER)
                .build();
        when(personRepository.findByEmail(email)).thenReturn(Optional.of(person));
        when(personMapper.toDto(person)).thenReturn(personDto);

        // When
        PersonDto result = personService.loadUserByEmail(email);

        // Then
        assertNotNull(result);
        assertEquals(personDto, result);
    }

    @Test
    void shouldThrowExceptionWhenEmailDoesNotExist() {
        // Given
        String email = "test@example.com";
        when(personRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomNotFoundException.class, () -> personService.loadUserByEmail(email));
    }

    @Test
    void shouldReturnAllPersons() {
        // Given
        List<Person> persons = new ArrayList<>();
        Person person = new Person();
        persons.add(person);
        PersonDto personDto = PersonDto.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .password("password")
                .dateOfBirth(new Date())
                .sex(Sex.MALE)
                .accountType(AccountType.USER)
                .build();
        when(personRepository.findAll()).thenReturn(persons);
        when(personMapper.toDto(person)).thenReturn(personDto);

        // When
        List<PersonDto> result = personService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(personDto, result.get(0));
    }
}