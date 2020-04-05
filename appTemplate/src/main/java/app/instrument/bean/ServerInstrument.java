package app.instrument.bean;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server instrument
 *
 * @author pbaioni
 */
public class ServerInstrument {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerInstrument.class);

	private String serverName;

	private ServerSocket serverSocket;

	private boolean serverIsListeningForConnections = true;

	private List<ClientProcessor> clientProcessors;

	public ServerInstrument() {

	}

	public ServerInstrument(String name, InetAddress address, int port, int maxConnections) {

		this.serverName = name;

		clientProcessors = new ArrayList<ClientProcessor>();

		try {
			this.serverSocket = new ServerSocket(port, maxConnections, address);
		} catch (IOException e) {
			LOGGER.error("Couldn't create server " + name, e);
		}

	}

	public void start() {

		// one thread per server
		Thread serverThread = new Thread(new Runnable() {
			public void run() {
				while (serverIsListeningForConnections) {

					try {
						// waiting for client connection
						Socket client = serverSocket.accept();

						ClientProcessor clientProcessor = new ClientProcessor(client);
						clientProcessors.add(clientProcessor);
						InetSocketAddress remote = (InetSocketAddress) client.getRemoteSocketAddress();

						// command reception log
						String origin = remote.getAddress().getHostAddress() + ":" + remote.getPort();
						LOGGER.info("Server " + serverName + " has received a new client connection from " + origin
								+ ", [activeConnections=" + clientProcessors.size() + "]");

						// one thread per client
						Thread processorThread = new Thread(clientProcessor);
						processorThread.start();

					} catch (SocketException e) {
						LOGGER.error("Socket is closed for " + serverName);
					} catch (IOException e) {
						LOGGER.error(e.getMessage());
					}

				}
			}
		});

		serverThread.start();

	}

	public void close() {

		//breaking loop for client connection waiting
		serverIsListeningForConnections = false;

		//closing all the processors for connected clients
		LOGGER.info("Closing all client processors for " + serverName);
		for (ClientProcessor processor : clientProcessors) {
			processor.close();
		}

		//closing the server socket
		try {
			serverSocket.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

		LOGGER.info("Server " + serverName + " has been shut down");

	}

	public void broadcast(String msg, String origin) {
		LOGGER.info("Server " + serverName + " is braodcasting >>> " + msg);
		for (ClientProcessor processor : clientProcessors) {
			if (!processor.getOrigin().equals(origin)) {
				processor.send(msg);
			}
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
