package app.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.persistence.repo.DummyRepository;

@Component
public class Application implements CommandLineRunner{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	DummyRepository repository;
	
	@Autowired
	ApplicationProperties properties;
	
	
	public void init() {
		
		LOGGER.info("App property: " + properties.getAppProperty());
		
	}
	
	public void start() {
		LOGGER.info("DB content: " + repository.findAll().toString());
	}
	
	public void stop() {
		
	}

	@Override
	public void run(String... args) throws Exception {
		init();
		start();
	}

}
