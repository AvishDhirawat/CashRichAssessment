package com.cashrich.CashRichAssessment.Controller;

import com.cashrich.CashRichAssessment.DTO.CoinDataDTO;
import com.cashrich.CashRichAssessment.Service.CoinDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/coins")
public class CoinDataController {

    @Autowired
    private CoinDataService coinDataService;

    @PostMapping("/save")
    public ResponseEntity<CoinDataDTO> saveCoinData(@RequestBody CoinDataDTO coinDataDTO) {
        CoinDataDTO savedCoinData = coinDataService.saveCoinData(coinDataDTO);
        return new ResponseEntity<>(savedCoinData, HttpStatus.CREATED);
    }

    // Add more endpoints as needed
}
