package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

	private ArrayList<ConnectionHandler> connections;
	private ServerSocket server;
	private boolean accepting;
	private ExecutorService pool;
	
	public Server() {
		connections = new ArrayList<ConnectionHandler>();
		accepting = true;
		try {
			server = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pool = Executors.newCachedThreadPool();
	}

	@Override
	public void run() {
		try {
			while(accepting) {
				Socket client = server.accept();
				ConnectionHandler connectionHandler = new ConnectionHandler(client);
				connections.add(connectionHandler);
				System.out.println("New connection accepted");
				pool.execute(connectionHandler);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastGameState(GameState gameState) {
		for (ConnectionHandler ch: connections) {
			if (ch != null) {
				ch.sendGameState(gameState);
			}
		}
	}
	
	public void shutdown() {
		accepting = false;
		if (! server.isClosed()) {
			try {
				server.close();
				for (ConnectionHandler ch: connections) {
					ch.shutdown();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ConnectionHandler implements Runnable {
		
		private Socket client;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		
		public ConnectionHandler(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				out = new ObjectOutputStream(client.getOutputStream());
				in = new ObjectInputStream(client.getInputStream());
				
				GameState gameState;
				try {
					while ((gameState = (GameState)in.readObject()) != null) {
						broadcastGameState(gameState);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	
		public void sendGameState(GameState state) {
			try {
				out.writeObject(state);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void shutdown() {
			if (! client.isClosed()) {
				try {
					in.close();
					out.close();
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
