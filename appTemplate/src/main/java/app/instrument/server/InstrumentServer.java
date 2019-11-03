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
	
	private Thread serverThread;
	
	private boolean isRunning = true;
	
	private List<ClientProcessor> processors;
	
	private List<Thread> clientThreads;

	public InstrumentServer() {

	}

	public InstrumentServer(String name, InetAddress address, int port, int maxConnections) {

		this.serverName = name;
		
		processors = new ArrayList<ClientProcessor>();
		
		clientThreads = new ArrayList<Thread>();

		try {
			this.serverSocket = new ServerSocket(port, maxConnections, address);
		} catch (IOException e) {
			LOGGER.error("Couldn't create server " + name, e);
		}

	}

	public void open() {

		// Toujours dans un thread à part vu qu'il est dans une boucle infinie
		serverThread = new Thread(new Runnable() {
			public void run() {
				while (isRunning == true) {

					try {
						// On attend une connexion d'un client
						Socket client = serverSocket.accept();
						ClientProcessor clientProcessor = new ClientProcessor(client, getServer());
						processors.add(clientProcessor);
						LOGGER.info("Server " + serverName + " has received a new client connection, [activeConnections=" + processors.size() + "]");

						Thread processorThread = new Thread(clientProcessor);
						clientThreads.add(processorThread);
						processorThread.start();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		serverThread.start();
		
	}

	public void close() {
		
		for (ClientProcessor processor : processors) {
			processor.close();
		}
		
		for(Thread clientThread : clientThreads) {
			clientThread.interrupt();
		}
		
		serverThread.interrupt();
		
		LOGGER.info("Server " + serverName + " has been shut down");

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
