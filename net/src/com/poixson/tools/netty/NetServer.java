package com.poixson.tools.netty;

import static com.poixson.tools.xDebug.IsDebug;
import static com.poixson.utils.Utils.SafeClose;

import java.net.InetAddress;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.SSLException;

import com.poixson.tools.abstractions.xStartable;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.DefaultExecutorServiceFactory;


public class NetServer implements xStartable {
	public static final int DEFAULT_SOCKET_BACKLOG = 10;

	protected final String key;
	protected final String bind;
	protected final int    port;

	protected final ServerBootstrap bootstrap;
	protected final SslContext context_ssl;

	protected final AtomicReference<Channel> server = new AtomicReference<Channel>(null);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);

	protected final CopyOnWriteArraySet<NetServerConnection> connections = new CopyOnWriteArraySet<NetServerConnection>();



	public NetServer(final String bind, final int port, final boolean ssl)
			throws InterruptedException, CertificateException, SSLException {
		this.bind = bind;
		this.port = port;
		this.key = this.build_key();
//TODO: use bind arg
//InetAddress.getLoopbackAddress()
//InetAddress.getByName(host)
		this.bootstrap = new ServerBootstrap();
		this.bootstrap.channel(NioServerSocketChannel.class);
		// socket threads
		final int threads = Runtime.getRuntime().availableProcessors() * 2;
		final EventLoopGroup group_boss   = new NioEventLoopGroup(1,       new DefaultExecutorServiceFactory("netty").newExecutorService(1));
		final EventLoopGroup group_worker = new NioEventLoopGroup(threads, new DefaultExecutorServiceFactory("netty").newExecutorService(threads));
		this.bootstrap.group(group_boss, group_worker);
		// socket backlog
		this.bootstrap.option(ChannelOption.SO_BACKLOG, DEFAULT_SOCKET_BACKLOG);
		// socket initializer
		final NetServerSocketInitializer initer = new NetServerSocketInitializer(this);
		this.bootstrap.childHandler(initer);
		// ssl
		if (ssl) {
//TODO: custom cert
			final SelfSignedCertificate cert = new SelfSignedCertificate();
			this.context_ssl = SslContext.newServerContext(cert.certificate(), cert.privateKey());
		} else {
			this.context_ssl = null;
		}
		this.bootstrap.childHandler(new NetServerSocketInitializer(this, this.context_ssl));
		// debug
		if (IsDebug()) {
			final LoggingHandler handlerLogger = new LoggingHandler(LogLevel.INFO);
			this.bootstrap.handler(handlerLogger);
			this.bootstrap.childHandler(handlerLogger);
		}
	}



	@Override
	public void start() {
		if (this.server.get() != null)
			throw new IllegalStateException("Socket server already started");
//TODO: use bind arg
		final InetAddress addr_local = null;
		// start listening
		final Channel server;
		try {
			server = this.bootstrap.bind(addr_local, this.port)
				.sync().channel();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		if (!this.server.compareAndSet(null, server))
			throw new IllegalStateException("Socket server already started");
	}
	@Override
	public void stop() {
		this.stopping.set(true);
		final Channel server = this.server.getAndSet(null);
		if (server != null) {
			try {
				server.close().sync();
			} catch (InterruptedException ignore) {}
		}
	}
	public int closeAll() {
		int count = 0;
		if (!this.connections.isEmpty()) {
//			this.log().info("Closing sockets..");
//TODO
		}
		return count;
	}



	public void register(final NetServerConnection connection) {
		this.connections.add(connection);
	}
	public boolean unregister(final NetServerConnection connection) {
		SafeClose(connection);
		return this.connections.remove(connection);
	}



	public void cleanup() {
		final Iterator<NetServerConnection> it = this.connections.iterator();
		while (it.hasNext()) {
			final NetServerConnection connection = it.next();
			if (connection.isClosed())
				it.remove();
		}
	}



	public int getSocketCount() {
		return this.connections.size();
	}



	@Override
	public boolean isRunning() {
		return (this.server.get() != null);
	}
	@Override
	public boolean isStopping() {
		if (!this.isRunning())
			return false;
		return this.stopping.get();
	}



	public String getServerKey() {
		return this.key;
	}
	protected String build_key() {
		return (new StringBuilder())
			.append(this.bind)
			.append(':').append(this.port)
			.toString();
	}



}
