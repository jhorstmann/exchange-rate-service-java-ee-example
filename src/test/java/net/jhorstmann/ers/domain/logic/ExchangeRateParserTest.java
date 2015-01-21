package net.jhorstmann.ers.domain.logic;

import net.jhorstmann.ers.domain.model.ExchangeRate;
import org.junit.Test;

import javax.xml.xpath.XPathExpressionException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExchangeRateParserTest {

    @Test
    public void shouldParseExchangeRates() throws XPathExpressionException, ParseException {
        ExchangeRateParser parser = new ExchangeRateParser();


        List<ExchangeRate> exchangeRates = parser.loadExchangeRates(
                ExchangeRateParserTest.class.getResourceAsStream("eurofxref-daily.xml"));

        assertNotNull("exchange rates should not be null", exchangeRates);

        assertEquals("should contain 31 exchange rates", 31, exchangeRates.size());
    }
}
