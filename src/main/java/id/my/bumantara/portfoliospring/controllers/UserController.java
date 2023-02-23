package id.my.bumantara.portfoliospring.controllers;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import id.my.bumantara.portfoliospring.models.User;
import id.my.bumantara.portfoliospring.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserRepository repo;
  private final MongoTemplate template;

  public UserController(UserRepository userRepository, MongoTemplate mongoTemplate) {
    this.repo = userRepository;
    this.template = mongoTemplate;
  }

  @GetMapping("")
  public List<User> findAll() {
    return repo.findAll();
  }

  @GetMapping("/{username}")
  public User getUserByUsername(
    @PathVariable String username
  ) {
    Query query = new Query().addCriteria(Criteria.where("username").is(username));
    User user = template.findOne(query, User.class);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    return user;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/add")
  public ResponseEntity<User> add(
    @Valid @RequestBody User user
  ) {
    return ResponseEntity.ok(repo.insert(user));
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{username}/delete")
  public void delete(
    @PathVariable String username
  ) {
    repo.delete(getUserByUsername(username));
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/{username}/put")
  public ResponseEntity<User> update(
    @PathVariable String username,
    @Valid @RequestBody User data
  ) {
    if (!username.equals(data.getUsername())) {
      return ResponseEntity.badRequest().build();
    }
    User user = getUserByUsername(username);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    User updated = repo.save(data);
    return ResponseEntity.ok(updated);
  }
}
