package net.jhorstmann.ers.domain.logic;

import java.io.InputStream;

import java.net.MalformedURLException;

import java.text.ParseException;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

import javax.inject.Inject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.xpath.XPathExpressionException;

import net.jhorstmann.ers.domain.model.ExchangeRate;
import net.jhorstmann.ers.repository.ExchangeRatesRepository;

@Singleton
public class ExchangeRateImporter {

    public static final String URL_ECB_EXCHANGE_RATES = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    @Inject
    private ExchangeRateParser parser;

    @Inject
    private ExchangeRatesRepository repository;

    @Inject
    private Client client;

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void run() throws MalformedURLException, XPathExpressionException, ParseException {
        System.out.println("Loading exchange rates");

        final Invocation request = client.target(URL_ECB_EXCHANGE_RATES).request(MediaType.APPLICATION_XML_TYPE)
                                         .buildGet();

        final Response response = request.invoke();
        response.bufferEntity();

        final InputStream inputStream = response.readEntity(InputStream.class);
        final List<ExchangeRate> exchangeRates = parser.loadExchangeRates(inputStream);

        repository.saveExchangeRates(exchangeRates);
    }
}
