package net.jhorstmann.ers.domain.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "exchange_rate", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "currency"})})
@Access(AccessType.FIELD)
@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ExchangeRate {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(nullable = false)
    @ValidCurrency
    private String currency;
    @Column(nullable = false, precision = 12, scale = 4, columnDefinition = "DECIMAL(12, 4)")
    private BigDecimal rate;

    public ExchangeRate() {
    }

    public ExchangeRate(Date date, String currency, BigDecimal rate) {
        this.date = date;
        this.currency = currency;
        this.rate = rate;
    }

    @XmlTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlAttribute
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlAttribute
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

}
