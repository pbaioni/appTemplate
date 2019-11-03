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
import app.instrument.bean.SocketInstrument;
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
		LOGGER.info("App property: " + properties.getAppProperty());
		LOGGER.info("Application initialized");
		userService.fillDB();
		
	}
	
	public void start() {
		LOGGER.info("Application started");

		LOGGER.info("DB content: " + userService.getAllUsers().toString());
		
		serverService.init();
		serverService.startServers();
		
		instrumentService.init();
		instrumentService.connectAll();
		
		try {
			List<Instrument> instruments = instrumentService.getInstruments();
			Instrument i1 =instrumentService.getInstrumentByName("client1");
			Instrument i2 =instrumentService.getInstrumentByName("client2");
			Instrument i3 =instrumentService.getInstrumentByName("client3");
			
			for(Instrument i : instruments) {
				i.sendAndRead("hello");
				((SocketInstrument)i).echo("test");
			}

			
			i1.send("broadcast message for all!");
			for(Instrument i : instruments) {
				if(!i.equals(i1)) {
					i.read();
				}
			}
			
			i1.sendAndRead("hello");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void stop() {
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
		stop();
	}

}
