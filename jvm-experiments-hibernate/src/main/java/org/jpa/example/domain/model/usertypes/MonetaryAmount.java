package org.jpa.example.domain.model.usertypes;

import com.google.common.base.Objects;
import kr.nsoft.commons.ValueObjectBase;
import kr.nsoft.commons.tools.HashTool;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * org.jpa.example.domain.model.usertypes.MonetaryAmount
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 6.
 */
public class MonetaryAmount extends ValueObjectBase {

    private static final long serialVersionUID = 3784911023192209715L;

    @Getter
    private final BigDecimal amount;
    @Getter
    private final Currency currency;

    public MonetaryAmount(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public MonetaryAmount convertTo(Currency targetCurrency) {
        BigDecimal targetAmount = new BigDecimal(amount.toBigInteger(),
                                                 targetCurrency.getDefaultFractionDigits());
        return new MonetaryAmount(targetAmount, targetCurrency);
    }

    @Override
    public int hashCode() {
        return HashTool.compute(amount, currency);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("amount", amount)
                .add("currency", currency);
    }
}
