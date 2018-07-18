package com.tank.service;

import com.tank.message.Person;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fuchun
 */
@Service
public class PersonService {

  private static List<Person> persons = null;

  static {
    persons = new LinkedList<>();
    persons.add(new Person().setName("lisi").setGender((byte) 1));
    persons.add(new Person().setName("xiaohong").setGender((byte) 0));
    persons.add(new Person().setName("liubei").setGender((byte) 1));
  }


  public Flux<Person> list() {
    return Flux.fromIterable(persons);
  }

  public Mono<Person> findPersonBy(@NonNull String name) {
    val personOpt = persons.parallelStream()
        .filter(p -> name.equalsIgnoreCase(p.getName()))
        .map(person -> Mono.just(person));
    return personOpt.findFirst().orElse(Mono.empty());
  }


  public Mono<Void> savePerson(Mono<Person> personMono) {
    return personMono.doOnNext(person -> {
      persons.add(person);
    }).thenEmpty(Mono.empty());
  }


}
