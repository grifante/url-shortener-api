package com.urlshortener.controller;

import static com.urlshortener.controller.ResultUtil.*;
import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.model.Stats;
import com.urlshortener.model.Url;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.service.StatsService;

@RestController
@RequestMapping("/stats")
public class StatsController {

	@Autowired
	private UrlRepository urlRepository;

	@Autowired
	private StatsService service;

	@GetMapping
	public ResponseEntity<Stats> getStats() {
		return ok(service.getStats());
	}

	@GetMapping("/{urlId}")
	public ResponseEntity<Url> getStats(@PathVariable long urlId) {
		return urlRepository.findById(urlId).map(ResponseEntity::ok).orElse(notFound());
	}

}
