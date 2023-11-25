package mmk.security.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mmk.security.app.entity.User;
import mmk.security.app.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User> getUsers() {
		return userService.findAll();
	}
	@GetMapping("/{userId}")
	public User getUserById(@PathVariable int userId) {
		return userService.findById(userId);
	}
	@GetMapping("/{email}/{password}")
	public User userLogin(@PathVariable String email, @PathVariable String password) {
		return userService.userLogin(email, password);
	}
	@PostMapping
	public User save(@RequestBody User user) {
		return userService.save(user);
	}
	@DeleteMapping("/{userId}")
	public boolean deleteUser(@PathVariable int userId) {
		userService.delete(userId);
		return true;
	}
}