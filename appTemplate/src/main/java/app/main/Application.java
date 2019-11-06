package app.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import app.instrument.Instrument;
import app.instrument.bean.ClientInstrument;
import app.instrument.service.InstrumentService;
import app.instrument.service.ServerService;
import app.main.properties.ApplicationProperties;
import app.persistence.services.UserService;

@Service
public class Application implements ApplicationRunner, DisposableBean{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	ApplicationProperties properties;
	
	@Autowired
	UserService userService;
	
	@Autowired
	InstrumentService instrumentService;
	
	@Autowired
	ServerService serverService;
	
	public void init() {
		//app property example
		LOGGER.info("App property: " + properties.getAppProperty());

		//initializing services
		userService.fillDB();
		serverService.init();
		instrumentService.init();
		
		LOGGER.info("Application initialized");
		
	}
	
	public void start() {

		//printing users DB content
		LOGGER.info("DB content: " + userService.getAllUsers().toString());
		
		//starting servers
		serverService.startServers();
		
		//connecting instruments
		instrumentService.connectAll();
		
		LOGGER.info("Application started");
		
		//sending and receiving some messages
		try {
			List<Instrument> instruments = instrumentService.getInstruments();
			
			for(Instrument i : instruments) {
				//using instrument method
				i.sendAndRead("hello");
				//using specific method
				((ClientInstrument)i).echo("test");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void stop() {
		LOGGER.info("Stopping Application");
		instrumentService.disconnectAll();
		serverService.closeServers();
		LOGGER.info("Application stopped");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		init();
		start();
	}

	@Override
	public void destroy() throws Exception {
		LOGGER.info("Destroying Application");
		stop();
	}

}
