package vitorcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vitorcode.dto.Exchange;
import vitorcode.environment.InstanceInformationService;
import vitorcode.model.Book;
import vitorcode.proxy.ExchangeProxy;
import vitorcode.repository.BookRepository;

import java.util.HashMap;

@RestController
@RequestMapping("book-service")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    InstanceInformationService instanceInformationService;
    @Autowired
    BookRepository repository;
    @Autowired
    ExchangeProxy exchangeProxy;

    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency){
        String port = instanceInformationService.getRetrieveServerPort();
        var book = repository.findById(id).orElseThrow();

        Exchange exchangeResponseBody = exchangeProxy.getExchange(book.getPrice(), "USD", currency);
        if(exchangeResponseBody == null) throw new RuntimeException("exchange response body é nulo");

        book.setCurrency(currency);
        book.setPrice(exchangeResponseBody.getConvertedValue());
        book.setEnvironment(port);

        return book;
    }
}
