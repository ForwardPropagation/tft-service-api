package com.fp.tft;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WiremockTestExtension extends WireMockServer implements BeforeAllCallback, AfterAllCallback, AfterEachCallback {

    public WiremockTestExtension(Options options) {
        super(options);
    }

    public WiremockTestExtension() {
        this(WireMockConfiguration.wireMockConfig().port(8889));
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        this.start();
        WireMock.configureFor("localhost", WiremockTestExtension.this.port());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        this.resetAll();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        this.stop();
    }
}
