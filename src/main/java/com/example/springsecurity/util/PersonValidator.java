package com.example.springsecurity.util;

import com.example.springsecurity.models.Person;
import com.example.springsecurity.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    //    В данном методе указываем для какой модели предназначен данный валидатор
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personService.findByLogin(person) != null){
            errors.rejectValue("login", "", "Данный логин уже зарегистрирован");
        }
    }
}
