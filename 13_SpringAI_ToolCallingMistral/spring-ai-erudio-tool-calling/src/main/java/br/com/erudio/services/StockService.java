package br.com.erudio.services;

import br.com.erudio.api.DailyStockData;
import br.com.erudio.api.StockData;
import br.com.erudio.api.StockRequest;
import br.com.erudio.api.StockResponse;
import br.com.erudio.settings.APISettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

public class StockService implements Function<StockRequest, StockResponse> {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final RestTemplate restTemplate;

    @Value("${TWELVE_DATA_API_KEY:none}")
    private String apiKey;

    public StockService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public StockResponse apply(StockRequest stockRequest) {
        StockData data = restTemplate.getForObject(
                APISettings.TWELVE_DATA_BASE_URL + "?symbol={0}&interval=1day&outputsize=1&apikey={1}",
                StockData.class,
                stockRequest.company(),
                apiKey
        );

        if (data == null || data.getValues() == null || data.getValues().isEmpty()) {
            logger.error("Failed to retrieve stock data or received empty values for: {}", stockRequest.company());
            throw new IllegalStateException("Invalid or empty response from stock API");
        }

        DailyStockData latestData = data.getValues().get(0);

        try {
            return new StockResponse(Float.parseFloat(latestData.getClose()));
        } catch (NumberFormatException e) {
            logger.error("Failed to parse closing price: {}", latestData.getClose(), e);
            throw new IllegalStateException("Invalid closing price format", e);
        }
    }
}