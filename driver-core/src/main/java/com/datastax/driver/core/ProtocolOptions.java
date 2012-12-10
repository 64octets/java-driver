package com.datastax.driver.core;

/**
 * Options of the Cassandra native binary protocol.
 */
public class ProtocolOptions {

    /**
     * Compression supported by the Cassandra binary protocol.
     */
    public enum Compression {
        /** No compression */
        NONE(""),
        /** Snappy compression */
        SNAPPY("snappy");

        final String protocolName;

        private Compression(String protocolName) {
            this.protocolName = protocolName;
        }

        @Override
        public String toString() {
            return protocolName;
        }
    };

    /**
     * The default port for Cassandra native binary protocol: 9042.
     */
    public static final int DEFAULT_PORT = 9042;

    private final int port;

    final SSLOptions sslOptions; // null if no SSL

    private volatile Compression compression = Compression.NONE;

    private volatile Cluster.Manager manager;

    /**
     * Creates a new {@code ProtocolOptions} instance using the {@code DEFAULT_PORT}
     * and doesn't use SSL.
     */
    public ProtocolOptions() {
        this(DEFAULT_PORT, null);
    }

    /**
     * Creates a new {@code ProtocolOptions} instance using the provided port
     * and SSL context.
     *
     * @param port the port to use for the binary protocol.
     * @param sslOptions the SSL options to use. Use {@code null} if SSL is not
     * to be used.
     */
    public ProtocolOptions(int port, SSLOptions sslOptions) {
        this.port = port;
        this.sslOptions = sslOptions;
    }

    void register(Cluster.Manager manager) {
        this.manager = manager;
    }

    /**
     * The port used to connect to the Cassandra hosts.
     *
     * @return the port used to connect to the Cassandra hosts.
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the compression used by the protocol.
     * <p>
     * The default compression is {@code Compression.SNAPPY}.
     *
     * @return the compression used.
     */
    public Compression getCompression() {
        return compression;
    }

    /**
     * Sets the compression to use.
     * <p>
     * Note that while this setting can be changed at any time, it will
     * only apply to newly created connections.
     *
     * @param compression the compression algorithm to use (or {@code
     * Compression.NONE} to disable compression).
     * @return this {@code ProtocolOptions} object.
     */
    public ProtocolOptions setCompression(Compression compression) {
        this.compression = compression;
        return this;
    }
}
