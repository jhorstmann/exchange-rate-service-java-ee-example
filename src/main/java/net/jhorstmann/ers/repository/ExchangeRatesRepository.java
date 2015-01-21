package net.jhorstmann.ers.repository;

import net.jhorstmann.ers.domain.model.ExchangeRate;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class ExchangeRatesRepository {
    @PersistenceContext(unitName = "er-unit", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public List<ExchangeRate> getLatest() {
        return entityManager.createQuery(
                "SELECT e FROM ExchangeRate e WHERE e.id = (SELECT max (e2.id) FROM ExchangeRate e2 WHERE e2.currency = e.currency)",
                ExchangeRate.class).getResultList();
    }

    public ExchangeRate getLatest(String source) {
        return entityManager.createQuery(
                "SELECT e FROM ExchangeRate e WHERE e.id = (SELECT max (e2.id) FROM ExchangeRate e2 WHERE e2.currency = e.currency) AND e.currency = :currency",
                ExchangeRate.class).setParameter(
                "currency", source).getSingleResult();
    }

    @Nullable
    public ExchangeRate findByCurrencyAndDate(String currency, Date date) {
        try {
        return entityManager.createQuery("SELECT e FROM ExchangeRate e WHERE e.currency = :currency AND e.date = :date",
                ExchangeRate.class)
                            .setParameter("currency", currency)
                            .setParameter("date", date, TemporalType.DATE)
                            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void saveExchangeRates(List<ExchangeRate> exchangeRates) {
        for (ExchangeRate exchangeRate : exchangeRates) {
            ExchangeRate existing = findByCurrencyAndDate(exchangeRate.getCurrency(), exchangeRate.getDate());
            if (existing == null) {
                entityManager.persist(exchangeRate);
            }
        }
    }
}
