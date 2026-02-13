package com.sist.web.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class AIController {
	@GetMapping("/main")
	public String main_page() {
		return "main";
	}
	
	@GetMapping("/sync")
	public String sync_page(Model model) {
		model.addAttribute("msg", "hello ThymLeaf + 일반 전송");
		return "sync";
	}
	
	@GetMapping("/stream")
	public Mono<String> stream_page(Model model) {
		model.addAttribute("msg", "hello ThymLeaf + WebFlux 사용");
		return Mono.just("stream");
	}
	// 타이핑 형식 => 채팅 / 챗봇 / 알림
	@GetMapping(value = "/stream2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<String>> webFlux_page() {
		return Flux.just(
				"Java : 웹 프로그램의 기본",
				"Oracle : CRUD 정리",
				"JSP / ThymLeaf : 자바기반의 웹 Front",
				"Spring / SpringBoot : 라이브러리",
				"SpringAI : JAP 형식"
		).delayElements(Duration.ofSeconds(3))
		 .map(data->ServerSentEvent.builder(data).build());
	}
}
