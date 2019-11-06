package app.instrument.bean;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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

	private Thread serverThread;

	private boolean isRunning = true;

	private List<ClientProcessor> processors;

	//private List<Thread> clientThreads;

	public ServerInstrument() {

	}

	public ServerInstrument(String name, InetAddress address, int port, int maxConnections) {

		this.serverName = name;

		processors = new ArrayList<ClientProcessor>();

		//clientThreads = new ArrayList<Thread>();

		try {
			this.serverSocket = new ServerSocket(port, maxConnections, address);
		} catch (IOException e) {
			LOGGER.error("Couldn't create server " + name, e);
		}

	}

	public void start() {

		// one thread per server
		serverThread = new Thread(new Runnable() {
			public void run() {
				while (isRunning == true) {

					try {
						// waiting for client connection
						Socket client = serverSocket.accept();
						ClientProcessor clientProcessor = new ClientProcessor(client);
						processors.add(clientProcessor);
						InetSocketAddress remote = (InetSocketAddress) client.getRemoteSocketAddress();

						// command reception log
						String origin = remote.getAddress().getHostAddress() + ":" + remote.getPort();
						LOGGER.info("Server " + serverName + " has received a new client connection from " + origin
								+ ", [activeConnections=" + processors.size() + "]");

						//one thread per client
						Thread processorThread = new Thread(clientProcessor);
						//clientThreads.add(processorThread);
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

//		for (Thread clientThread : clientThreads) {
//			clientThread.interrupt();
//		}

		serverThread.interrupt();

		LOGGER.info("Server " + serverName + " has been shut down");

	}

	private ServerInstrument getServer() {
		return this;
	}

	public void broadcast(String msg, String origin) {
		LOGGER.info("Server " + serverName + " is braodcasting >>> " + msg);
		for (ClientProcessor processor : processors) {
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
