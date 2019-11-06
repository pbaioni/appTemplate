package app.instrument.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import app.instrument.server.InstrumentServer;

@Service
public class ServerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerService.class);
	

	InstrumentServer server1;
	InstrumentServer server2;

	List<InstrumentServer> serverList;

	public ServerService() {
		serverList = new ArrayList<InstrumentServer>();
	}

	public void init() {
		try {
			
			InetAddress address = InetAddress.getByName("localhost");
			server1 = new InstrumentServer("Server1", address, 10001, 10);
			server2 = new InstrumentServer("Server2", address, 10002, 10);
			serverList.add(server1);
			serverList.add(server2);


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void startServers() {
		for (InstrumentServer server : serverList) {
			server.open();
			LOGGER.info(server.getServerName() + " is listeneing on " + server.getServerSocket().getInetAddress().toString() + ":" + server.getServerSocket().getLocalPort());
		}
	}

	public void closeServers() {
		for (InstrumentServer server : serverList) {
			server.close();
		}
		LOGGER.info("All servers have been stopped");
	}

}
