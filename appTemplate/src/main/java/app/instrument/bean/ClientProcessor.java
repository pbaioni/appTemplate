package app.instrument.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.persistence.model.UserDo;
import app.web.api.model.User;

public class ClientProcessor implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientProcessor.class);

	private Socket clientSocket;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private boolean isRunning = true;
	private String origin;

	public ClientProcessor(Socket pSock) {
		clientSocket = pSock;
		InetSocketAddress remote = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
		origin = remote.getAddress().getHostAddress() + ":" + remote.getPort();
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

			// waiting for a message from client
			String commandLine = read();

			if (!Objects.isNull(commandLine)) {

				// command reception log
				LOGGER.info("Server has received " + commandLine + " from: " + origin);

				try {
					manageCommands(commandLine);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	private void manageCommands(String commandLine) throws Exception {

		Scanner scanner = new Scanner(commandLine);
		scanner.useDelimiter(" ");
		if (scanner.hasNext()) {
			String command = scanner.next();
			String allArguments = "";
			List<String> arguments = new ArrayList<String>();
			try {
				while (scanner.hasNext()) {
					String arg = scanner.next();
					arguments.add(arg);
					allArguments += arg + " ";
				}
			} catch (NoSuchElementException e) {
				// DO NOTHING
			}

			scanner.close();

			// treating command
			String response = "";

			switch (command.toUpperCase()) {
			case "HELLO":
				response = "Hello, client!";
				break;
			case "ECHOING":
				response = allArguments.trim();
				break;
			case "CLOSE":
				close();
				break;
			default:
				response = "Unknown command";
				break;
			}

			if (!response.equals("")) {
				send(response);
			}
		}

	}

	public void send(String msg) {
		LOGGER.info("Sending " + msg + " to " + origin);
		writer.println(msg);
		writer.flush();
	}

	public String read() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public String getOrigin() {
		return origin;
	}

	public void close() {
		LOGGER.info("Closing processor " + getOrigin());
		isRunning = false;
		try {
			LOGGER.info("Closing streams for " + getOrigin());
			reader.close();
			writer.close();
			if (clientSocket.isConnected()) {
				LOGGER.info("Closing client socket for " + getOrigin());
				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
