package com.tank.handler;

import com.tank.message.Person;
import com.tank.service.PersonService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author fuchun
 */
@Component
public class PersonHandler {

  public Mono<ServerResponse> list(ServerRequest request) {
    val data = personService.list();
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(data, Person.class);
  }

  public Mono<ServerResponse> queryBy(ServerRequest request) {
    val name = request.pathVariable("name");
    val notFound = ServerResponse.badRequest().contentType(APPLICATION_JSON).body(fromObject("not exists"));
    val data = personService.findPersonBy(name);
    return data.flatMap(p -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(fromObject(p))).switchIfEmpty(notFound);
  }

  public Mono<ServerResponse> savePerson(ServerRequest request) {
    Mono<Person> person = request.bodyToMono(Person.class);
    return ServerResponse.ok().build(this.personService.savePerson(person));
  }



  @Autowired
  private PersonService personService;
}
