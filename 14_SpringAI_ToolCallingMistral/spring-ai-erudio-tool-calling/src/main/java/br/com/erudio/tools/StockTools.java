package br.com.erudio.tools;

import br.com.erudio.api.DailyShareQuote;
import br.com.erudio.api.DailyStockData;
import br.com.erudio.api.StockData;
import br.com.erudio.api.StockResponse;
import br.com.erudio.settings.APISettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class StockTools {

    private static final Logger LOG = LoggerFactory.getLogger(StockTools.class);

    private RestTemplate restTemplate;
    @Value("${TWELVE_DATA_API_KEY:none}")
    String apiKey;

    public StockTools(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Tool(description = "Latest stock prices")
    public StockResponse getLatestStockPrices(@ToolParam(description = "Name of company") String company) {
        LOG.info("Get stock prices for: {}", company);
        StockData data = restTemplate.getForObject(APISettings.TWELVE_DATA_BASE_URL + "?symbol={0}&interval=1min&outputsize=1&apikey={1}",
                StockData.class,
                company,
                apiKey);
        DailyStockData latestData = data.getValues().get(0);
        LOG.info("Get stock prices ({}) -> {}", company, latestData.getClose());
        return new StockResponse(Float.parseFloat(latestData.getClose()));
    }

    @Tool(description = "Historical daily stock prices")
    public List<DailyShareQuote> getHistoricalStockPrices(@ToolParam(description = "Search period in days") int days,
                                                          @ToolParam(description = "Name of company") String company) {
        LOG.info("Get historical stock prices: {} for {} days", company, days);
        StockData data = restTemplate.getForObject(APISettings.TWELVE_DATA_BASE_URL + "?symbol={0}&interval=1day&outputsize={1}&apikey={2}",
                StockData.class,
                company,
                days,
                apiKey);
        return data.getValues().stream()
                .map(d -> new DailyShareQuote(company, Float.parseFloat(d.getClose()), d.getDatetime()))
                .toList();
    }
}
