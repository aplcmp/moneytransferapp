package com.thecompany.moneytransfer.converters;

import com.thecompany.moneytransfer.dos.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CurrencyRatesConverter {

    public static final int scale = 10;
    private final Map<String, Integer> ccys;
    private final BigDecimal[][] matrix;
    private final BigDecimal one = new BigDecimal("1");
    private int index = 0;

    public CurrencyRatesConverter(List<Rate> rates) {
        HashMap<String, Integer> ccysFill = new HashMap<>();
        matrix = new BigDecimal[rates.size()+1][];
        for (int i=0; i < matrix.length; ++i) {
            matrix[i] = new BigDecimal[matrix.length];
            matrix[i][i] = one;
        }

        ccysFill.put("USD", index);
        rates.stream().forEach(rate -> {
            Integer prev = ccysFill.put(rate.getCcy2(), ++index);
            if (prev != null) {
                throw new IllegalStateException("Duplicated mapping to " + rate.getCcy2());
            }
            matrix[0][index] = rate.getRatio(); // USD/CCY
            matrix[index][0] = one.divide(rate.getRatio(), scale, RoundingMode.FLOOR); // CCY/USD
        });

        ccys = Collections.unmodifiableMap(ccysFill);

        BigDecimal one = new BigDecimal("1");
        for (int i=1; i < matrix.length; ++i) {
            for (int j=i+1; j < matrix.length; ++j) {
                matrix[i][j] = matrix[0][j].divide(matrix[0][i], scale, RoundingMode.FLOOR);
                matrix[j][i] = matrix[0][i].divide(matrix[0][j], scale, RoundingMode.FLOOR);
            }
        }

    }

    public Map<String, Integer> getCcys() {
        return ccys;
    }

    public BigDecimal getRate(String ccy1, String ccy2) throws InvalidCurrencyException {
        final Integer ccy1i = ccys.get(ccy1), ccy2i = ccys.get(ccy2);
        if (ccy1i == null || ccy2i == null) {
            throw new InvalidCurrencyException((ccy1i == null)?ccy1:ccy2);
        }
        return matrix[ccy1i][ccy2i];
    }

}
