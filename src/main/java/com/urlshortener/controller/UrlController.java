package com.urlshortener.controller;

import static com.urlshortener.controller.ResultUtil.notFound;
import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.urlshortener.model.Url;
import com.urlshortener.repository.UrlRepository;

@RestController
public class UrlController {

	@Autowired
	private UrlRepository repository;
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@PreDestroy
	public void shutdonw() {
		executor.shutdown();
	}

	@GetMapping
	public Object gerUrl(HttpServletRequest request) {
		return repository.findByShortUrl(request.getRequestURL().toString()).map(this::getRedirect).orElse(notFound());
	}

	@GetMapping("/urls/{urlId}")
	public Object gerUrl(@PathVariable Long urlId) {
		return repository.findById(urlId).map(this::getRedirect).orElse(notFound());
	}

	private Object getRedirect(Url url) {
		executor.submit(() -> repository.incrementHits(url.getId()));
		RedirectView redirectView = new RedirectView(url.getUrl());
		redirectView.setStatusCode(MOVED_PERMANENTLY);
		return redirectView;
	}

	@DeleteMapping("/urls/{id}")
	private void delete(@PathVariable Long id) {
		repository.delete(id);
	}

}
