package domain.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import domain.KUAlchemistsGame;
import domain.Server;

public class HostHandler {
	
	private Server server;
	private String IPAddress;
	private int port;
	private ExecutorService pool;
	
	public void startServer() {
		server = new Server();
		port = 9999;
		pool = Executors.newCachedThreadPool();
		pool.execute(server);
	}

	public String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getPort() {
		return port;
	}

	public void login(String username, int avatar) {
		KUAlchemistsGame.getInstance().createPlayer(username, avatar);
		HandlerFactory.getInstance().getJoinHandler().connectToServer(getIPAddress(), Integer.toString(port));
		KUAlchemistsGame.getInstance().setDevicePlayer(username);
	}
}