package com.example.springsecurity.services;

import com.example.springsecurity.models.Person;
import com.example.springsecurity.repositories.PersonRepository;
import com.example.springsecurity.security.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        получить пользователя по логину
        Optional<Person> person = personRepository.findByLogin(username);
//        выбрасываем исключение о том, что пользователь не найден. Будет поймано Спринг
//        Секьюрити и сообщнеие будет выведено на страницу
        if(person.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(person.get());
    }
}
