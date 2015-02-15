package net.jhorstmann.ers.api.rates;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import net.jhorstmann.ers.domain.model.ExchangeRates;
import net.jhorstmann.ers.domain.model.ValidCurrency;
import net.jhorstmann.ers.repository.ExchangeRatesRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Stateless
@Path("exchange-rates")
@Api("exchange-rates")
@Produces({"application/xml"})
public class ExchangeRatesResource {
    @Inject
    private ExchangeRatesRepository exchangeRatesRepository;

    @GET
    @Path("/latest/{currency}")
    @Produces({"application/xml"})
    @ApiOperation(value = "Retrieves the latest exchange rate from EUR to the given currency")
    public ExchangeRates getLatestRates(@PathParam("currency") @ApiParam("currency") @ValidCurrency String currency) {
        ExchangeRates rates = new ExchangeRates();
        rates.getRates().add(exchangeRatesRepository.getLatest(currency));
        return rates;
    }

    @GET
    @Path("/latest")
    @Produces({"application/xml"})
    @ApiOperation(value = "Retrieves the latest exchange rates from EUR to all currencies")
    public ExchangeRates getRates() {
        ExchangeRates rates = new ExchangeRates();
        rates.getRates().addAll(exchangeRatesRepository.getLatest());
        return rates;
    }
}
