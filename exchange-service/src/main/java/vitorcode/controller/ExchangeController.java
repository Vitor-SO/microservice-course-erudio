package vitorcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitorcode.environment.InstanceInformationService;
import vitorcode.model.Exchange;
import vitorcode.repository.ExchangeRepository;

import java.math.BigDecimal;

@RestController
@RequestMapping("exchange-service")
public class ExchangeController {
    private static final Logger log = LoggerFactory.getLogger(ExchangeController.class);
    @Autowired
    InstanceInformationService instanceInformationService;
    @Autowired
    ExchangeRepository repository;

    @GetMapping(value ="/{amount}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Exchange getExchange(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from, @PathVariable("to") String to){
        Exchange exchange = repository.findByFromAndTo(from,to);
        if(exchange == null) throw new RuntimeException("Currency unsupported");
        BigDecimal conversionFactor = exchange.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        exchange.setConvertedValue(convertedValue);
        exchange.setEnvironment("PORT " + instanceInformationService.getRetrieveServerPort());
        return exchange;
    }
}
