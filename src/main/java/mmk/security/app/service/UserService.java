package mmk.security.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mmk.security.app.entity.User;
import mmk.security.app.repostory.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	public User userLogin(String email, String password) {
		var user = findByEmail(email);
		if(encoder.matches(password, user.getPassword()))
			return user;
		
		return null;
	}
	public void delete(int prjId) {
		userRepo.deleteById(prjId);
	}
	public List<User> findAll(){
		return userRepo.findAll();
	}
	public User findById(int id) {
		return userRepo.findById(id).get();
	}
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
}