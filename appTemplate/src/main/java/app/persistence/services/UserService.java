package app.persistence.services;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.persistence.exceptions.EntityNotFoundException;
import app.persistence.model.UserDo;
import app.persistence.properties.UsersProperties;
import app.persistence.repo.UserRepository;

@Component
public class UserService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
		
	@Autowired
	UserRepository repository;
	
	@Autowired
	UsersProperties properties;
	
	public void fillDB() {
		Date now = new Date();
		UserDo paolo = new UserDo("Paolo", "pistache", now);
		UserDo clemence = new UserDo("Clemence", "pommepoire", now);
		UserDo superFan = new UserDo("superFan", "LoveYou", now);
		repository.save(paolo);
		repository.save(clemence);
		repository.save(superFan);
		
		LOGGER.info("Database filled");
	}

	public Iterable<UserDo> getAllUsers() {
		return repository.findAll();
	}

	public UserDo getUser(String userName) {
		return repository.findByUserName(userName);
	}

	public UserDo create(UserDo user) {
		return repository.save(user);
	}

	public UserDo updateUser(UserDo user) {
		UserDo userToUpdate = repository.findById(user.getUserName()).orElseThrow(EntityNotFoundException::new);
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setLastLogin(user.getLastLogin());
		return repository.save(userToUpdate);
	}

	public void deleteUser(String userName) {
      UserDo userToDelete = repository.findById(userName)
      .orElseThrow(EntityNotFoundException::new);
    repository.delete(userToDelete);
		
	}
	
}
