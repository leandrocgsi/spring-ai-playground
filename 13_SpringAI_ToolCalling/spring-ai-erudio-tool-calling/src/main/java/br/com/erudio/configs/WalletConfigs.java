package br.com.erudio.configs;

import br.com.erudio.api.StockRequest;
import br.com.erudio.api.StockResponse;
import br.com.erudio.api.WalletResponse;
import br.com.erudio.repositories.WalletRepository;
import br.com.erudio.services.StockService;
import br.com.erudio.services.WalletService;
import br.com.erudio.tools.StockTools;
import br.com.erudio.tools.WalletTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class WalletConfigs {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Description("Number of shares for each company in my portfolio")
    public Supplier<WalletResponse> numberOfShares(WalletRepository walletRepository) {
        return new WalletService(walletRepository);
    }

    @Bean
    @Description("Latest stock prices")
    public Function<StockRequest, StockResponse> latestStockPrices() {
        return new StockService(restTemplate());
    }

    @Bean
    public StockTools stockTools() {
        return new StockTools(restTemplate());
    }

    @Bean
    public WalletTools walletTools(WalletRepository walletRepository) {
        return new WalletTools(walletRepository);
    }
}
