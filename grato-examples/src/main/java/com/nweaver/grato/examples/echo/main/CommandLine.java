package com.nweaver.grato.examples.echo.main;

import com.nweaver.grato.Server;
import com.nweaver.grato.ServerException;
import com.nweaver.grato.examples.echo.EchoListener;
import com.nweaver.grato.impl.DefaultServer;
import com.nweaver.grato.impl.DefaultServerContext;

public class CommandLine {
	/*
	 * Block instantiation. 
	 */
	protected CommandLine() {
	}
	
	private void addShutdownHook(final Server engine) {
		Runnable shutdownHook = new Runnable() {
			@Override
			public void run() {
				System.out.println("Stopping server...");
				engine.stop();
			}
		};
		
		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new Thread(shutdownHook));
	}
	
	public static void main(String[] args) throws ServerException {
		CommandLine cli = new CommandLine();
		
		DefaultServerContext context = new DefaultServerContext();
		//context.addListener("echo", new EchoListener(null, 3000, 0));
		
		Server server = new DefaultServer(context);
		server.start();
		System.out.println("Server started");
		
		cli.addShutdownHook(server);
	}
}
