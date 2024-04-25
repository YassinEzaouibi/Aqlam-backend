package aqlaam.version2.controller;

import aqlaam.version2.dto.PersonDto;
import aqlaam.version2.service.interfaces.IPersonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/person")
@RequiredArgsConstructor
public class PersonController {

    private final IPersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        logger.info("Fetching all persons");
        List<PersonDto> persons = personService.getAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN', 'USER')")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {
        logger.info("Fetch person with id: {}", id);
        PersonDto person = personService.findPersonById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

}
