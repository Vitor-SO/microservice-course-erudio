package vitorcode.controller;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("resilience")
public class ResilienceController {

    @GetMapping("/teste")
    @Retry(name = "resilience", fallbackMethod = "fbResilience")
    public String resilienceRetry(){
        return new RestTemplate().getForEntity("http://localhost:8080/resilience", String.class)
                .getBody();
    }

    public String fbResilience(Exception ex){
        return "fallback resilience {} "+ ex.getMessage();
    }
}
