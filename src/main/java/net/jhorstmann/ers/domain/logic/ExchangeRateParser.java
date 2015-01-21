package net.jhorstmann.ers.domain.logic;

import net.jhorstmann.ers.domain.model.ExchangeRate;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class ExchangeRateParser {
    private static final String NS_ECB_EXCHANGE_RATES = "http://www.ecb.int/vocabulary/2002-08-01/eurofxref";

    public List<ExchangeRate> loadExchangeRates(String uri) throws XPathExpressionException, ParseException {
        InputSource source = new InputSource(uri);

        return getExchangeRates(source);
    }

    public List<ExchangeRate> loadExchangeRates(InputStream inputStream) throws XPathExpressionException, ParseException {
        InputSource source = new InputSource(inputStream);

        return getExchangeRates(source);
    }

    private List<ExchangeRate> getExchangeRates(InputSource source) throws XPathExpressionException, ParseException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new SimpleNamespaceContext("ecb", NS_ECB_EXCHANGE_RATES));
        XPathExpression rateExpression = xpath.compile("//ecb:Cube[@currency]");
        XPathExpression timeExpression = xpath.compile("../@time");

        NodeList nodes = (NodeList) rateExpression.evaluate(source, XPathConstants.NODESET);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        for (int i = 0, len = nodes.getLength(); i < len; i++) {
            Node cube = nodes.item(i);
            NamedNodeMap attributes = cube.getAttributes();
            String currency = attributes.getNamedItem("currency").getNodeValue();
            String rate = attributes.getNamedItem("rate").getNodeValue();
            String time = (String) timeExpression.evaluate(cube, XPathConstants.STRING);

            ExchangeRate exchangeRate = new ExchangeRate(format.parse(time), currency, new BigDecimal(rate));
            exchangeRates.add(exchangeRate);
        }

        return exchangeRates;
    }
}
