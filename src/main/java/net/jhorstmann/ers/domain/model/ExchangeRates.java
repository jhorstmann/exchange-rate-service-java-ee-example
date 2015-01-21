package net.jhorstmann.ers.domain.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExchangeRates {

    private List<ExchangeRate> rates;

    public List<ExchangeRate> getRates() {
        if (rates == null) {
            rates = new ArrayList<>();
        }
        return rates;
    }
}
