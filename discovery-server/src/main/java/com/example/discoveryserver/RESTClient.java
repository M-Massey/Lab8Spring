package com.example.discoveryserver;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@SpringBootApplication
@EnableDiscoveryClient
public class RESTClient extends SpringBootServletInitializer{
    public static void main(String[] args) throws IOException {
        getBook("abcd");
    }

    public static void putBook(String title, String author, String isbn) {
        try (CloseableHttpClient client = HttpClients.createDefault()){
            HttpPut put = new HttpPut(String.format("http://localhost:8080/myapp/library/%s/%s/%s",encodeValue(author),encodeValue(title),encodeValue(isbn)));
            CloseableHttpResponse response = client.execute(put);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    public static String getBook(String isbn){
        try(CloseableHttpClient client = HttpClients.createDefault()){

            HttpGet httpGet = new HttpGet(String.format("http://localhost:8080/myapp/library/%s",encodeValue(isbn)));

            CloseableHttpResponse response = client.execute(httpGet);
            return readResponse(response);
        }
        catch(Exception e){
            e.printStackTrace();
            return "Failed to get book";
        }
    }

    public static String readResponse(CloseableHttpResponse response) throws IOException {
        Scanner sc = new Scanner(response.getEntity().getContent());
        StringBuilder stringResponse = new StringBuilder();

        while(sc.hasNext()){
            stringResponse.append(java.net.URLDecoder.decode(sc.nextLine(), StandardCharsets.UTF_8.name()));
            stringResponse.append("\n");
        }
        response.close();
        return stringResponse.toString();
    }
}
