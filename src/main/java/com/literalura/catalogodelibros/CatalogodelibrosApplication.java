package com.literalura.catalogodelibros;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.catalogodelibros.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CatalogodelibrosApplication implements CommandLineRunner {

    @Autowired
    private Principal principal;

    public static void main(String[] args) {
        SpringApplication.run(CatalogodelibrosApplication.class, args);
    }
    @Override
    public void run(String... args) {
        Principal principal = new Principal();
        while (true) {
            principal.mostrarElMenu();
        }
    }
}




