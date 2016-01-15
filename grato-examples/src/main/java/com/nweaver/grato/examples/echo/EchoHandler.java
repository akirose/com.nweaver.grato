package com.nweaver.grato.examples.echo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nweaver.grato.ServerContext;
import com.nweaver.grato.core.service.AbstractIoHandler;
import com.nweaver.grato.listener.Listener;

public class EchoHandler extends AbstractIoHandler {
	
	private final Logger LOG = LoggerFactory.getLogger(EchoHandler.class);
	
	private ServerContext context;
	private Listener listener;

	public void init(ServerContext context, Listener listener) {
		this.context = context;
		this.listener = listener;
	}
	
}
