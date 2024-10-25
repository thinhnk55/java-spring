package work.vietdefi.spring.controller;

import org.springframework.web.bind.annotation.*;
import work.vietdefi.spring.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // In-memory storage for demo purposes
    private final Map<Long, User> userStorage = new ConcurrentHashMap<>();

    // 1. Create a new User (POST)
    @PostMapping
    public User createUser(@RequestBody User user) {
        userStorage.put(user.getUserId(), user);
        return user;
    }

    // 2. Get a User by ID (GET)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userStorage.getOrDefault(id, null);
    }

    // 3. Get all Users (GET)
    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(userStorage.values());
    }

    // 4. Update a User by ID (PUT)
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (userStorage.containsKey(id)) {
            userStorage.put(id, updatedUser);
            return updatedUser;
        }
        throw new NoSuchElementException("User not found with ID: " + id);
    }

    // 5. Delete a User by ID (DELETE)
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        if (userStorage.remove(id) != null) {
            return "User with ID " + id + " deleted successfully.";
        }
        return "User not found.";
    }
}
