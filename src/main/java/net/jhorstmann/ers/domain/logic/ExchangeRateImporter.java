package net.jhorstmann.ers.domain.logic;

import java.net.MalformedURLException;

import java.text.ParseException;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

import javax.inject.Inject;

import javax.xml.xpath.XPathExpressionException;

import net.jhorstmann.ers.framework.flowid.RunWithFlowId;

@Singleton
@RunWithFlowId
public class ExchangeRateImporter {

    @Inject
    private ExchangeRateImportService exchangeRateImportService;

    @Schedule(second = "*/20", minute = "*", hour = "*", persistent = false)
    public void run() throws MalformedURLException, XPathExpressionException, ParseException {
        exchangeRateImportService.run();
    }
}
