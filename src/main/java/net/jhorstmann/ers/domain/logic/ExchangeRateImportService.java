package net.jhorstmann.ers.domain.logic;

import net.jhorstmann.ers.domain.model.ExchangeRate;
import net.jhorstmann.ers.framework.flowid.RunWithFlowId;
import net.jhorstmann.ers.repository.ExchangeRatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.xpath.XPathExpressionException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

@RunWithFlowId
public class ExchangeRateImportService {
    private static final String URL_ECB_EXCHANGE_RATES = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateImportService.class);

    @Inject
    private ExchangeRateParser parser;

    @Inject
    private ExchangeRatesRepository repository;

    @Inject
    private Client client;

    public void run() throws XPathExpressionException, ParseException {
        LOG.info("Loading exchange rates");

        final Invocation request = client.target(URL_ECB_EXCHANGE_RATES).request(MediaType.APPLICATION_XML_TYPE)
                                         .buildGet();

        final Response response = request.invoke();
        response.bufferEntity();

        final InputStream inputStream = response.readEntity(InputStream.class);
        final List<ExchangeRate> exchangeRates = parser.loadExchangeRates(inputStream);

        repository.saveExchangeRates(exchangeRates);

    }
}
