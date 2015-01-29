package net.jhorstmann.ers.framework.jaxrs;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import javax.inject.Singleton;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientProvider {

    @Produces
    @Singleton
    public Client newClient() {
        return ClientBuilder.newBuilder().register(FlowIdClientRequestFilter.class).build();
    }

    public void close(@Disposes final Client client) {
        client.close();
    }
}
