package net.jhorstmann.ers.domain.logic;

import java.io.InputStream;

import java.text.ParseException;

import java.util.List;

import javax.inject.Inject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.xpath.XPathExpressionException;

import net.jhorstmann.ers.domain.model.ExchangeRate;
import net.jhorstmann.ers.framework.flowid.RunWithFlowId;
import net.jhorstmann.ers.repository.ExchangeRatesRepository;

@RunWithFlowId
public class ExchangeRateImportService {
    public static final String URL_ECB_EXCHANGE_RATES = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    @Inject
    private ExchangeRateParser parser;

    @Inject
    private ExchangeRatesRepository repository;

    @Inject
    private Client client;

    public void run() throws XPathExpressionException, ParseException {
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
