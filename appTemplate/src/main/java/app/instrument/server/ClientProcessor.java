package app.instrument.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientProcessor implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientProcessor.class);

	private Socket clientSocket;
	private InstrumentServer server;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private boolean isRunning = true;

	public ClientProcessor(Socket pSock, InstrumentServer server) {
		clientSocket = pSock;
		this.server = server;
	}

	@Override
	public void run() {

		try {
			// getting client socket io
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			writer = new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e) {
			LOGGER.error("Error while getting client socket IO", e);
		}

		while (isRunning) {

			try {

				// waiting for a message from client
				String command = reader.readLine();

				if (!Objects.isNull(command)) {
					InetSocketAddress remote = (InetSocketAddress) clientSocket.getRemoteSocketAddress();

					// debug log
					String debug = "";
					debug += "Received " + command + " from: " + remote.getAddress().getHostAddress() + ":"
							+ remote.getPort();
					LOGGER.info(debug);

					// treating command
					String response = "";

					switch (command.toUpperCase()) {
					case "HELLO":
						response = "Hello, client!";
						break;
					case "BROADCAST":
						server.broadcast(command);
						break;
					case "CLOSE":
						writer = null;
						reader = null;
						clientSocket.close();
						break;
					default:
						response = "Unknown command";
						break;
					}

					send(response);
				}

			} catch (SocketException e) {
				LOGGER.error("Socket error while processing command", e);
			} catch (IOException e) {
				LOGGER.error("IO error while processing command", e);
			}
		}

	}

	public void send(String msg) {
		LOGGER.info("Responding " + msg);
		writer.println(msg);
		writer.flush();
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void close() {
		isRunning = false;
		try {
			reader.close();
			writer.close();
			if (clientSocket.isConnected()) {

				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
