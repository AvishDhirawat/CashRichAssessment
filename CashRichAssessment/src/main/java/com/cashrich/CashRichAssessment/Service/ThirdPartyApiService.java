package com.cashrich.CashRichAssessment.Service;

import com.cashrich.CashRichAssessment.DTO.ApiResponse;
import com.cashrich.CashRichAssessment.DTO.ThirdPartyApiRequest;
import com.cashrich.CashRichAssessment.Entity.ThirdPartyApiLog;
import com.cashrich.CashRichAssessment.Repository.ThirdPartyApiLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class ThirdPartyApiService {

//    @Value("${thirdpartyapi.baseurl}")
    private String baseUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETHLTC";

//    @Value("${thirdpartyapi.apikey}")
    private String apiKey = "27ab17d1-215f-49e5-9ca4-afd48810c149";

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ThirdPartyApiLogRepository thirdPartyApiLogRepository;

//    public ThirdPartyApiService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    public ApiResponse callApi(ThirdPartyApiRequest request) {
        String apiUrl = baseUrl + "?param1=" + request.getParam1() + "&param2=" + request.getParam2();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, httpEntity, String.class);

        String responseBody = response.getBody();
        ApiResponse apiResponse = new ApiResponse("Success", responseBody);
        return apiResponse;
    }

    public void saveRequestResponse(Long userId, ThirdPartyApiRequest request, ApiResponse response) {
        ThirdPartyApiLog log = new ThirdPartyApiLog();
        log.setUserId(userId); // Set the user ID
        log.setRequest(request.toString());
        log.setResponse(response.toString());
        log.setTimestamp(LocalDateTime.now());

        thirdPartyApiLogRepository.save(log);
    }

}

