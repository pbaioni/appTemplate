package app.instrument.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.instrument.Instrument;
import app.instrument.helper.InstrumentHelper;

@Service
public class InstrumentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentService.class);

	@Autowired
	InstrumentsDefinitions instrumentsDefinition;

	List<Instrument> instruments;

	public InstrumentService() {
		instruments = new ArrayList<Instrument>();
	}

	public void init() {

		InstrumentDefinition def1 = new InstrumentDefinition("client1", "SocketInstrument", "127.0.0.1:10001", 5000);
		instruments.add(InstrumentHelper.getBeanForModel(def1));

//		InstrumentDefinition def2 = new InstrumentDefinition("client2", "SocketInstrument", "127.0.0.1:10002", 5000);
//		instruments.add(InstrumentHelper.getBeanForModel(def2));
		
		LOGGER.info("Instrument init done");
	}

	public void connectAll() {
		LOGGER.info("Connecting all instruments");
		for (Instrument i : instruments) {
			connect(i);
		}
	}

	public void disconnectAll() {
		for (Instrument i : instruments) {
			disconnect(i);
		}
	}

	public void connect(String instrumentName) {
		connect(getInstrumentByName(instrumentName));
	}

	public void disconnect(String instrumentName) {
		disconnect(getInstrumentByName(instrumentName));
	}

	private void connect(Instrument i) {
		try {
			i.connect();
			LOGGER.info("Instrument " + i.getName() + " connected to " + i.getAddress());
		} catch (IOException e) {
			LOGGER.error("Impossible to connect " + i.getName() + "@" + i.getAddress(), e);
		}
	}

	private void disconnect(Instrument i) {
		try {
			if (i.isConnected()) {
				i.disconnect();
				LOGGER.info("Instrument " + i.getName() + " disconnected from " + i.getAddress());
			}
		} catch (IOException e) {
			LOGGER.error("Problem while disconnecting " + i.getName(), e);
		}
	}

	public List<Instrument> getInstruments() {
		return instruments;
	}

	public Instrument getInstrumentByName(String name) {

		for (Instrument i : instruments) {
			if (i.getName().equals(name)) {
				return i;
			}
		}

		return null;
	}

}
