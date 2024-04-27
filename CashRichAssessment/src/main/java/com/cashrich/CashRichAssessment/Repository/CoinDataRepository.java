package com.cashrich.CashRichAssessment.Repository;

import com.cashrich.CashRichAssessment.Entity.CoinData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinDataRepository extends JpaRepository<CoinData, Long> {
}
