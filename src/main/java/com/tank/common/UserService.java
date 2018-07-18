package com.tank.common;

import com.tank.message.User;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * @author fuchun
 */
@Service
public class UserService {


  public Mono<ResponseEntity<Map<String, Integer>>> userNum() {
    val users = new HashMap<String, Integer>(16);
    val response = new HashMap<String, Integer>();
    users.putIfAbsent("lisi", 22);
    users.putIfAbsent("wangwu", 23);
    return Objects.isNull(users) ? Mono.create(t -> {
      val rs = new HashMap<String, Integer>(16);
      rs.putIfAbsent("count", 0);
      t.success(ResponseEntity.status(HttpStatus.NO_CONTENT).body(rs));
    }) : Mono.create(t -> {
      response.putIfAbsent("count", users.size());
      t.success(ResponseEntity.status(HttpStatus.OK).body(response));
    });
  }



  public Flux<User> users() {
    val users = new LinkedList<User>();
    users.add(new User().setName("lisi").setAge(22));
    users.add(new User().setName("wangwu").setAge(32));
    return Flux.fromIterable(users).delayElements(Duration.ofSeconds(2));
  }
}
