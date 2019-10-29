package app.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import app.persistence.repo.DummyRepository;
import app.services.DummyService;

@Service
public class Application implements ApplicationRunner, DisposableBean{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	DummyService service;
	
	@Autowired
	ApplicationProperties properties;
	
	
	public void init() {
		LOGGER.info("App property: " + properties.getAppProperty());
		LOGGER.info("Application initialized");
		
	}
	
	public void start() {
		LOGGER.info("Application started");
		LOGGER.info("DB content: " + service.findAll().toString());
	}
	
	public void stop() {
		//NOTHING TO DO
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		init();
		start();
	}

	@Override
	public void destroy() throws Exception {
		stop();
	}

}
