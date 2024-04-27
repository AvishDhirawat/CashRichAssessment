package com.cashrich.CashRichAssessment.Service;

import com.cashrich.CashRichAssessment.DTO.CoinDataDTO;
import com.cashrich.CashRichAssessment.Entity.CoinData;
import com.cashrich.CashRichAssessment.Repository.CoinDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinDataService {

    @Autowired
    private CoinDataRepository coinDataRepository;

    public CoinDataDTO saveCoinData(CoinDataDTO coinDataDTO) {
        // Convert DTO to Entity
        CoinData coinData = new CoinData();
        coinData.setCoinName(coinDataDTO.getCoinName());
        coinData.setPrice(coinDataDTO.getPrice());
        coinData.setMarketCap(coinDataDTO.getMarketCap());

        // Save coin data to database
        CoinData savedCoinData = coinDataRepository.save(coinData);

        // Convert Entity to DTO and return
        return convertToDTO(savedCoinData);
    }

    // Helper method to convert Entity to DTO
    private CoinDataDTO convertToDTO(CoinData coinData) {
        CoinDataDTO coinDataDTO = new CoinDataDTO();
        coinDataDTO.setId(coinData.getId());
        coinDataDTO.setCoinName(coinData.getCoinName());
        coinDataDTO.setPrice(coinData.getPrice());
        coinDataDTO.setMarketCap(coinData.getMarketCap());
        return coinDataDTO;
    }
}

