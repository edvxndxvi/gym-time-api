package br.com.fiap.gym_time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.gym_time.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}