package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	
	private Socket client;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String address;
	private int port;

	public Client(String address, int port) {
		this.address = address;
		this.port = port;
		try {
			client = new Socket(this.address, this.port);
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void broadcastChange(GameState state) {
		try {
			out.writeObject(state);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			GameState gameState;
			try {
				while((gameState = (GameState)in.readObject()) != null) {
					gameState.updateGameState();
					System.out.println("Game State received");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		try {
			in.close();
			out.close();
			if (! client.isClosed()) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
