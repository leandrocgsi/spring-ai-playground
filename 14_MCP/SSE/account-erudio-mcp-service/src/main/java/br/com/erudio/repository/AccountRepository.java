package br.com.erudio.repository;

import br.com.erudio.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByPersonId(Long personId);
}
