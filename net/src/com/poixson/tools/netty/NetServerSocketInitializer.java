package com.poixson.tools.netty;

import java.nio.charset.Charset;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;


public class NetServerSocketInitializer extends ChannelInitializer<SocketChannel> {

	protected final NetServer server;
	protected final SslContext context_ssl;

	protected static final Charset charset = Charset.forName("UTF8");
	protected static final StringDecoder strDecoder = new StringDecoder(charset);
	protected static final StringEncoder strEncoder = new StringEncoder(charset);



	public NetServerSocketInitializer(final NetServer server) {
		this(server, null);
	}
	public NetServerSocketInitializer(final NetServer server, final SslContext context_ssl) {
		this.server      = server;
		this.context_ssl = context_ssl;
	}



	@Override
	protected void initChannel(final SocketChannel channel) throws Exception {
		// register new socket state
		final NetServerConnection connection = new NetServerConnection(this.server, channel);
//TODO: where to put this?
//		socketState.setSessionState(SessionState.PREAUTH);
		this.server.register(connection);
//TODO
//		xLog.Get().info("Accepted connection from: %s", connection.getSource());
//		this.log(socketState).info("Accepted connection from: "+socketState.getStateKey());
		// setup pipeline
		final ChannelPipeline pipe = channel.pipeline();
		if (this.context_ssl != null)
			pipe.addLast(this.context_ssl.newHandler(channel.alloc()));
//TODO: is this right?
		pipe.addLast(strDecoder);
		pipe.addLast(strEncoder);
		pipe.addLast(new JsonObjectDecoder());
//TODO
//		pipe.addLast(connection.getHandler());
//TODO: where to put this?
//		// listen for hello packet
//		Packet_0_Hello.init(
//				socketState.getPacketState()
//		);
	}



}
