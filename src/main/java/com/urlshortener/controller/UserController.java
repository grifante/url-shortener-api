package com.urlshortener.controller;

import static com.urlshortener.controller.ResultUtil.conflict;
import static com.urlshortener.controller.ResultUtil.created;
import static com.urlshortener.controller.ResultUtil.notFound;
import static org.springframework.http.ResponseEntity.ok;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.model.Stats;
import com.urlshortener.model.Url;
import com.urlshortener.model.User;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.repository.UserRepository;
import com.urlshortener.service.StatsService;

@RestController
public class UserController {

	private static final int RANDOM_STRING_LENGTH = 7;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UrlRepository urlRepository;

	@Autowired
	private StatsService service;

	@Value("${base.url}")
	private String baseUrl;

	@GetMapping("/users/{userId}/stats")
	public ResponseEntity<Stats> getStats(@PathVariable String userId) {
		return userRepository.findById(userId).map(user -> ok(service.getStats(userId))).orElse(notFound());
	}

	@PostMapping("/users")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		try {
			userRepository.insert(user.getId());
			return created(user);
		} catch (DataIntegrityViolationException dive) {
			return conflict();
		}
	}

	@PostMapping("/users/{userId}/urls")
	public ResponseEntity<Url> addUrl(@PathVariable String userId, @RequestBody Url url) {
		try {
			url.setUserId(userId);
			url.setShortUrl(baseUrl + "/" + RandomStringUtils.randomAlphanumeric(RANDOM_STRING_LENGTH));
			return created(urlRepository.save(url));
		} catch (DataIntegrityViolationException dive) {
			return addUrl(userId, url);
		}
	}

	@DeleteMapping("/user/{userId}")
	public void delete(@PathVariable String userId) {
		userRepository.delete(userId);
	}

}
