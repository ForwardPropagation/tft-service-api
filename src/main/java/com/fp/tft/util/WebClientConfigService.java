package com.fp.tft.util;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

/**
 * Utility Class for configuring WebClient
 */
@Component
@RequiredArgsConstructor
public class WebClientConfigService {

    /**
     * Create an instance of ReactorClientHttpConnector configured with timeouts
     * @param connectTimeout
     * @param readTimeout
     * @return ClientHttpConnector
     */
    public ClientHttpConnector getClientHttpConnector(final int connectTimeout, final int readTimeout) {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client -> configureTcpClient(client, connectTimeout, readTimeout))
                .wiretap(true);

        return new ReactorClientHttpConnector(httpClient);
    }

    /**
     * Configure a TcpClient with timeout in milliseconds
     * @param client
     * @param connectTimeout
     * @param readTimeout
     * @return TcpClient
     */
    protected TcpClient configureTcpClient(TcpClient client, final int connectTimeout, final int readTimeout) {
        return client
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)));
    }
}
