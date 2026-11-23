package dasturlash.uz.service;

import dasturlash.uz.dto.sms.SmsAuthDTO;
import dasturlash.uz.dto.sms.SmsAuthResponseDTO;
import dasturlash.uz.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SmsSenderService {
    private final SmsHistoryService smsHistoryService;
    private final RestTemplate restTemplate;

    @Value("${sms.my_provider.username}")
    private String username;

    @Value("${sms.my_provider.password}")
    private String password;

    public void sendRegistrationSms(String phone){
        Integer code = RandomUtil.fiveDigit();
        String body = "kunuz.uz portali. Ro'yxatdan o'tish uchun tasdiqlash kodi: " + code;
        //sendSms(phone, body);
        sendSms(phone, "Bu Eskiz dan test");
        smsHistoryService.create(body, String.valueOf(code), phone);
    }

    // send message
    private void sendSms(String phonNumber, String message) {
        Map<String, String> body = new HashMap<>();
        body.put("mobile_phone", phonNumber);
        body.put("message", message);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + login());

        String url = "http://localhost:8081/api/v1/sms/send";
        RequestEntity<Map<String, String>> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        System.out.println("Sms send response: status " + response.getStatusCode());
        System.out.println("Sms send response: body " + response.getBody());
    }

    private String login(){
        SmsAuthDTO dto = new SmsAuthDTO();
        dto.setUsername(this.username);
        dto.setPassword(this.password);

        String url = "http://localhost:8081/api/v1/client/auth/login";
        SmsAuthResponseDTO result = restTemplate.postForObject(url, dto, SmsAuthResponseDTO.class);
        return result.getJwt();
    }
}
