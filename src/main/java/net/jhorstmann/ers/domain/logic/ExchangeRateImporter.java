package net.jhorstmann.ers.domain.logic;

import net.jhorstmann.ers.domain.model.ExchangeRate;
import net.jhorstmann.ers.repository.ExchangeRatesRepository;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

@Singleton
public class ExchangeRateImporter {

    public static final String URL_ECB_EXCHANGE_RATES = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    @Inject
    private ExchangeRateParser parser;

    @Inject
    private ExchangeRatesRepository repository;

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void run() throws MalformedURLException, XPathExpressionException, ParseException {
        System.out.println("Loading exchange rates");
        List<ExchangeRate> exchangeRates = parser.loadExchangeRates(URL_ECB_EXCHANGE_RATES);
        repository.saveExchangeRates(exchangeRates);
    }
}
