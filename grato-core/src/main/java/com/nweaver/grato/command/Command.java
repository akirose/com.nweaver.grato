package com.nweaver.grato.command;

import java.io.IOException;

import org.apache.mina.core.session.IoSession;

import com.nweaver.grato.ServerContext;
import com.nweaver.grato.ServerException;
import com.nweaver.grato.core.Request;

/*
 * This interface encapsulates all commands.
 * @author Minki,Cho
 */
public interface Command {
	/*
	 * Execute command.
	 * 
	 * @param session The current {@link MobileSession}
	 * @param context The current {@link ServerContext}
	 * @param request The current {@link MobileRequest}
	 * @throw IOException
	 * @throw ServerException
	 */
	abstract void execute(IoSession session, ServerContext context, Request request) throws IOException, ServerException;
}