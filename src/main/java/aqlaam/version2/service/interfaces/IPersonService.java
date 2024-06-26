package aqlaam.version2.service.interfaces;

import aqlaam.version2.dto.PersonDto;

import java.util.List;

public interface IPersonService {

    PersonDto loadUserByEmail(String email);

    PersonDto findPersonById(Long id);

    List<PersonDto> getAll();

}
