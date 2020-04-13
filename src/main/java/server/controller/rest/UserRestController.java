package server.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.User;
import server.service.UserService;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        ResponseEntity<List<User>> response = ResponseEntity.ok(userService.getAll());
        return response;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (userService.isExist(id)) {
            userService.deleteById(id);
            return ResponseEntity.ok("User with id = " + id + " was deleted");
        } else {
            return ResponseEntity.ok("User with id = " + id + " doesn't exist");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<User> getByName(@PathVariable String name) {
        User user = userService.getByName(name);
        ResponseEntity<User> response = ResponseEntity.ok(user);
        return response;
    }
}
