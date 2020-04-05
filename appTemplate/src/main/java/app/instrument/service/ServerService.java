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

import app.instrument.bean.ServerInstrument;

/**
 * Server Service
 *
 * @author pbaioni
 */
@Service
public class ServerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerService.class);
	

	ServerInstrument server1;
	ServerInstrument server2;

	List<ServerInstrument> serverList;

	public ServerService() {
		serverList = new ArrayList<ServerInstrument>();
	}

	public void init() {
		try {
			//TODO: create servers from configuration file
			InetAddress address = InetAddress.getByName("localhost");
			server1 = new ServerInstrument("Server1", address, 10001, 10);
			server2 = new ServerInstrument("Server2", address, 10002, 10);
			serverList.add(server1);
			serverList.add(server2);


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void startServers() {
		for (ServerInstrument server : serverList) {
			server.start();
			LOGGER.info(server.getServerName() + " is listeneing on " + server.getServerSocket().getInetAddress().toString() + ":" + server.getServerSocket().getLocalPort());
		}
	}

	public void closeServers() {
		LOGGER.info("Closing servers...");
		for (ServerInstrument server : serverList) {
			server.close();
		}
		LOGGER.info("All servers have been stopped");
	}

}
