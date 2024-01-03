package pe.inuk.notionproxyresume.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Controller
public class NotionProxyResumeController {

    private final WebClient webClient;


    public NotionProxyResumeController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/fetchHtml")
    public Mono<ResponseEntity<String>> fetchHtml() {
        String url = "https://9143jihyeonkim.notion.site/4fb6176b3d8c4fcfac99cfb787969cb1";

        // 외부 URL에서 HTML을 가져오기
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(htmlStr -> log.info(htmlStr))
                .map(htmlStr -> ResponseEntity.ok(htmlStr));
    }

    @GetMapping("/_assets/{fileName}")
    @ResponseBody
    public Mono<ResponseEntity<String>> proxyStaticFile(@PathVariable String fileName) {

        log.info("_assert called.");

        // 외부 URL에서 HTML을 가져오기
        return webClient.get()
                .uri("https://9143jihyeonkim.notion.site/_assets/{fileName}", fileName)
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlStr -> ResponseEntity.ok(htmlStr));
    }
    @PostMapping("/api/v3/getUserAnalyticsSettings")
    @ResponseBody
    public Mono<ResponseEntity<String>> proxyPostGetUserAnalyticSettings() {
        log.info("api called.");

        // JSON 응답 데이터 생성
        String jsonResponse = "{\n" +
                "    \"isIntercomEnabled\": true,\n" +
                "    \"isZendeskEnabled\": true,\n" +
                "    \"isLoggingEnabled\": true,\n" +
                "    \"isAmplitudeEnabled\": true,\n" +
                "    \"isSegmentEnabled\": true,\n" +
                "    \"intercomAppId\": \"gpfdrxfd\",\n" +
                "    \"noIntercomUserId\": false,\n" +
                "    \"isSprigEnabled\": true,\n" +
                "    \"isLoaded\": true\n" +
                "}";

        // Mono로 감싸서 ResponseEntity로 반환
        return Mono.just(ResponseEntity.ok(jsonResponse));
    }

    @PostMapping("/api/v3/getClientExperimentsV2")
    @ResponseBody
    public String proxyPostPing() {
        return "getClientExperimentsV2.json";
    }

    @PostMapping("/api/v3/getAssetsJsonV2")
    @ResponseBody
    public String proxy123131() {
        return "{}";
    }

    @PostMapping("/api/v3/ping")
    @ResponseBody
    public Mono<ResponseEntity<String>> proxyPost123() {
        log.info("ping called.");

        return Mono.just(ResponseEntity.ok("{}"));
    }

    @PostMapping("/api/v3/{fileName}")
    @ResponseBody
    public Mono<ResponseEntity<String>> proxyPost(@PathVariable String fileName, @RequestBody String payload, @RequestHeader HttpHeaders headers) {
        log.info("api called." + fileName);

        // 외부 URL로 POST 요청을 보내기
        return webClient.post()
                .uri("https://9143jihyeonkim.notion.site/api/v3/{fileName}", fileName)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlStr -> ResponseEntity.ok(htmlStr));
    }

/*

    {
    "isIntercomEnabled": true,
    "isZendeskEnabled": true,
    "isLoggingEnabled": true,
    "isAmplitudeEnabled": true,
    "isSegmentEnabled": true,
    "intercomAppId": "gpfdrxfd",
    "noIntercomUserId": false,
    "isSprigEnabled": true,
    "isLoaded": true
    }

*/


    @GetMapping("/{fileName}")
    @ResponseBody
    public Mono<ResponseEntity<String>> proxyStaticFileEtc(@PathVariable String fileName) {

        log.info("proxyStaticFileEtc called. fileName=", fileName);

        // 외부 URL에서 HTML을 가져오기
        return webClient.get()
                .uri("https://9143jihyeonkim.notion.site/{fileName}", fileName)
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlStr -> ResponseEntity.ok(htmlStr));
    }
    
    //위와 같은 방식으로 모든 요청에 대해 proxy 처리를 하여 응답 가능하도록 개선해볼 것


}
