package br.com.erudio.tools;

import br.com.erudio.entities.Share;
import br.com.erudio.repositories.WalletRepository;
import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

public class WalletTools {

    private WalletRepository walletRepository;

    public WalletTools(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Tool(description = "Number of shares for each company in my wallet")
    public List<Share> getNumberOfShares() {
        List<Share> shares = walletRepository.findAll();
        return shares;
    }
}
