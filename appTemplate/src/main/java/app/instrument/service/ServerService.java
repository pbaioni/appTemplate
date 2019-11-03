package app.instrument.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ServerService implements CommandLineRunner, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerService.class);

	ServerSocket server1;
	ServerSocket server2;

	List<ServerSocket> serverList;
	
	List<Thread> serverThreads;

	private boolean started;

	public ServerService() {
		serverList = new ArrayList<ServerSocket>();
		serverThreads = new ArrayList<Thread>();
		started = false;
	}

	public void init() {
		try {
			InetAddress address = InetAddress.getByName("localhost");
			server1 = new ServerSocket(10001, 10, address);
			server2 = new ServerSocket(10002, 10, address);
			serverList.add(server1);
			serverList.add(server2);


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void startServers() {
		for (ServerSocket server : serverList) {
			
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Socket client = server.accept();
						InputStream input = client.getInputStream();
						OutputStream output = client.getOutputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		                PrintWriter writer = new PrintWriter(output, true);
		                String response = "Server is alive";
		                
						while(true) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String line = reader.readLine();
							LOGGER.info("Server received: " + line);
							LOGGER.info("Server sending >>> " + response);
							writer.println(response);
							writer.flush();
						
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
			
			serverThreads.add(t);
			t.start();
			LOGGER.info("Server listeneing on " + server.getInetAddress().toString() + ":" + server.getLocalPort());

		}
		
		started = true;

	}

	public void closeServers() {
		for (ServerSocket server : serverList) {
			if (!server.isClosed()) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isStarted() {
		return started;
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@Override
	public void destroy() throws Exception {
		closeServers();
		LOGGER.info("Servers closed");
		for(Thread t : serverThreads) {
			t.interrupt();
		}
		
		LOGGER.info("Threads interrupted");

	}

}
