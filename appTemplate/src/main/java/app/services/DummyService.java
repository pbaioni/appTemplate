package app.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.persistence.model.DummyEntity;
import app.persistence.repo.DummyRepository;
import app.services.properties.DummyProperties;

@Component
public class DummyService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DummyService.class);
		
	@Autowired
	DummyRepository repository;
	
	@Autowired
	DummyProperties properties;
	
	public void initDB() {
		DummyEntity paolo = new DummyEntity("Paolo", 42, "Italian");
		DummyEntity clemence = new DummyEntity("Clemence", 36, "French");
		DummyEntity pereNoel = new DummyEntity("pereNoel", 299, "Finnish");
		repository.save(paolo);
		repository.save(clemence);
		repository.save(pereNoel);
		
		LOGGER.info("Database init done");
	}

	public List<DummyEntity> findAll() {
		return repository.findAll();
	}
}
