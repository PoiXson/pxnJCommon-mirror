/*
package com.poixson.tools.netty;

import java.io.IOException;
import java.net.InetSocketAddress;

import io.netty.channel.socket.SocketChannel;


public class NetServerConnection extends NetConnection {

	protected final NetServer server;
	protected final SocketChannel channel;

//	protected final NetServerHandler handler;
//	protected final PacketState packet_state;



	public NetServerConnection(final NetServer server, final SocketChannel channel) {
		this.server  = server;
		this.channel = channel;
//		this.handler = new NetServerHandler(server, this);
//		this.packet_state = new PacketState(server, this);
	}



	public void send(final String data) {
		this.getChannel().writeAndFlush(data);
	}



	@Override
	public void close() throws IOException {
		super.close();
		IOException e1 = null;
		IOException e2 = null;
		try {
			super.close();
		} catch (IOException e) {
			e1 = e;
		}
		try {
			this.getChannel().close().sync();
		} catch (InterruptedException e) {
			e2 = new IOException(e);
		}
		if (e1 != null) throw e1;
		if (e2 != null) throw e2;
	}



	public SocketChannel getChannel() {
		if (this.isClosed())
			return null;
		return this.channel;
	}



//TODO
//	public ChannelInboundHandlerAdapter getHandler() {
//		return this.handler;
//	}



//TODO
//	public PacketState getPacketState() {
//		return this.packet_state;
//	}



	@Override
	protected String build_key() {
		final InetSocketAddress remote = this.channel.remoteAddress();
		return (new StringBuilder())
			.append('[').append(this.index).append("] ")
			.append(remote.getHostName())
			.append(':').append(remote.getPort())
			.append(" -> ").append(this.server.getServerKey())
			.toString();
	}



}
*/
