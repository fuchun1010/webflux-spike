package com.tank.router;

import com.tank.handler.PersonHandler;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author fuchun
 */
@Configuration
@EnableWebFlux
public class RoutingConfiguration {

  @Bean
  public RouterFunction<ServerResponse> personRouter() {
    val personListRouter = GET("/person/list").and(JSON_FORMATTER);
    val personCreateRouter = POST("/person/create").and(JSON_FORMATTER);
    val personQueryRouter = GET("/person/{name}").and(JSON_FORMATTER);
    return route(personListRouter, personHandler::list)
        .andRoute(personCreateRouter, personHandler::savePerson)
        .andRoute(personQueryRouter, personHandler::queryBy);
  }


  @Bean
  public RouterFunction<ServerResponse> userRouter() {
    val userListerRouter = GET("/user/list").and(JSON_FORMATTER);
    return route(userListerRouter, personHandler::list);
  }

  private final RequestPredicate JSON_FORMATTER = accept(APPLICATION_JSON);

  @Autowired
  private PersonHandler personHandler;
}
