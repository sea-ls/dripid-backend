package ru.seals.delivery.service.impl;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.seals.delivery.model.Currency;
import ru.seals.delivery.repository.CurrencyRepository;
import ru.seals.delivery.service.GoogleSheetService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleSheetServiceImpl implements GoogleSheetService {
    // TODO: 29.04.2024 все константы заменить как закзачик сегенрит api key
    private static final String APPLICATION_NAME = "application";
    private static final String SPREADSHEET_ID = "1hUnMTxxaloJhbFBKy_NCIUmAhwO77TQZVoIbnFVyCHQ";
    private static final String API_KEY = "AIzaSyBZkk_CpGFdjz6noc8D4IDNHTl0geJIZ7U";
    private static final String RANGE = "Sheet1!A2:E5";

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public void updateCurrencyValues() {
        currencyRepository.saveAll(getCurrenciesFromSheet());
    }

    private List<Currency> getCurrenciesFromSheet() {
        try {
            List<List<Object>> values = getValues();
            List<Currency> currencies = new ArrayList<>(values.size());

            if (!values.isEmpty()) {
                for (List<Object> row : values) {
                    Currency currency = new Currency();
                    currency.setId(Long.parseLong(row.get(0).toString()));
                    currency.setCurrencyName(row.get(1).toString());
                    currency.setUpdatedTimestamp(LocalDateTime.now());
                    currency.setQuicklyPurchase(new BigDecimal(row.get(2).toString()));
                    currency.setNonQuicklyPurchase(new BigDecimal(row.get(3).toString()));
                    currency.setCrypto(new BigDecimal(row.get(4).toString()));

                    currencies.add(currency);
                }
            } else log.error("Currency from sheet is empty");

            return currencies;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Sheets getSheets() {
        NetHttpTransport transport = new NetHttpTransport.Builder().build();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpRequestInitializer httpRequestInitializer = request -> {
            request.setInterceptor(intercepted -> intercepted.getUrl().set("key", API_KEY));
        };

        return new Sheets.Builder(transport, jsonFactory, httpRequestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private List<List<Object>> getValues() throws IOException {
        return getSheets()
                .spreadsheets()
                .values()
                .get(SPREADSHEET_ID, RANGE)
                .execute()
                .getValues();
    }
}
