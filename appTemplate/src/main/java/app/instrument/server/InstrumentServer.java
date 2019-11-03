package app.instrument.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstrumentServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentServer.class);

	private String serverName;

	private ServerSocket serverSocket;
	
	private boolean isRunning = true;
	
	private List<ClientProcessor> processors;

	public InstrumentServer() {

	}

	public InstrumentServer(String name, InetAddress address, int port, int maxConnections) {

		this.serverName = name;
		
		processors = new ArrayList<ClientProcessor>();

		try {
			this.serverSocket = new ServerSocket(port, maxConnections, address);
		} catch (IOException e) {
			LOGGER.error("Couldn't create server " + name, e);
		}

	}

	public void open() {

		// Toujours dans un thread à part vu qu'il est dans une boucle infinie
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (isRunning == true) {

					try {
						// On attend une connexion d'un client
						Socket client = serverSocket.accept();
						ClientProcessor clientProcessor = new ClientProcessor(client, getServer());
						processors.add(clientProcessor);
						LOGGER.info("Server " + serverName + " has received a new client connection, [activeConnections=" + processors.size() + "]");

						Thread t = new Thread(clientProcessor);
						t.start();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
					serverSocket = null;
				}
			}
		});

		t.start();
	}

	public void close() {
		isRunning = false;
	}
	
	private InstrumentServer getServer() {
		return this;
	}
	
	public void broadcast(String msg) {
		for (ClientProcessor processor : processors) {
			processor.send(msg);
		}
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

}
