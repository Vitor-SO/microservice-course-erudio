package vitorcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitorcode.model.Exchange;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    Exchange findByFromAndTo(String from, String to);
}
