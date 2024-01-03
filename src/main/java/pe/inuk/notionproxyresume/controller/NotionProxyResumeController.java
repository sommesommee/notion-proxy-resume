package pe.inuk.notionproxyresume.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class NotionProxyResumeController {

    private final WebClient webClient;


    public NotionProxyResumeController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/fetchHtml")
    public Mono<ResponseEntity<String>> fetchHtml() {
        String url = "https://glass-goal-398.notion.site/IMyPhone-1dbcc5f5136745f883c8bf6aba755bd7?pvs=4";

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
        // 외부 URL에서 HTML을 가져오기
        return webClient.get()
                .uri("https://glass-goal-398.notion.site/_assets/{fileName}", fileName)
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlStr -> ResponseEntity.ok(htmlStr));
    }

    @GetMapping("/{fileName}")
    @ResponseBody
    public Mono<ResponseEntity<String>> proxyStaticFileNew(@PathVariable String fileName) {
        // 외부 URL에서 HTML을 가져오기
        return webClient.get()
                .uri("https://glass-goal-398.notion.site/{fileName}", fileName)
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlStr -> ResponseEntity.ok(htmlStr));
    }
    
    //위와 같은 방식으로 모든 요청에 대해 proxy 처리를 하여 응답 가능하도록 개선해볼 것

}
