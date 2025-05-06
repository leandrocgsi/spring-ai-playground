package br.com.erudio.tools;

import br.com.erudio.model.Account;
import br.com.erudio.repository.AccountRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTools {

    private AccountRepository accountRepository;

    public AccountTools(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Tool(description = "Find all accounts by person ID")
    public List<Account> getAccountsByPersonId(
            @ToolParam(description = "Person ID") Long personId) {
        return accountRepository.findByPersonId(personId);
    }
}
