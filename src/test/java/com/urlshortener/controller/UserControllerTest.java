package com.urlshortener.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.urlshortener.model.Stats;
import com.urlshortener.model.Url;
import com.urlshortener.model.User;

public class UserControllerTest extends BaseControllerTest {

	@Test
	public void testGetStatsUserNotFound() {
		ResponseEntity<Stats> response = restTemplate.getForEntity("/users/user-a/stats", Stats.class);
		assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
	}

	@Test
	public void testGetStatsUser() {
		User user = userRepository.save(new User("user-a"));

		Url urlA = new Url();
		urlA.setUserId(user.getId());
		urlA.setHits(5);
		urlA.setUrl("http://www.google.com");
		urlA.setShortUrl("http://localhost:8181/PrNuQZB");
		urlRepository.save(urlA);

		Url urlB = new Url();
		urlB.setUserId(user.getId());
		urlB.setUrl("http://www.google.com");
		urlB.setShortUrl("http://localhost:8181/PrNuQHJ");
		urlRepository.save(urlB);

		ResponseEntity<Stats> response = restTemplate.getForEntity("/users/" + user.getId() + "/stats", Stats.class);
		Stats stats = response.getBody();

		assertThat(stats.getHits()).isEqualTo(5);
		assertThat(stats.getUrlCount()).isEqualTo(2);
		assertThat(stats.getTopUrls()).hasSize(2);
	}

	@Test
	public void testAddUser() {
		HttpEntity<String> httpEntity = new HttpEntity<>("{\"id\": \"uear-a\"}", getHeaders());
		ResponseEntity<String> responseEntityCreated = restTemplate.exchange("/users", POST, httpEntity, String.class);
		assertThat(responseEntityCreated.getStatusCode()).isEqualTo(CREATED);
	}

	@Test
	public void testAddUserConflict() {
		HttpEntity<String> httpEntity = new HttpEntity<>("{\"id\": \"usear-a\"}", getHeaders());

		ResponseEntity<String> responseEntityCreated = restTemplate.exchange("/users", POST, httpEntity, String.class);
		assertThat(responseEntityCreated.getStatusCode()).isEqualTo(CREATED);

		ResponseEntity<String> responseEntityConflict = restTemplate.exchange("/users", POST, httpEntity, String.class);
		assertThat(responseEntityConflict.getStatusCode()).isEqualTo(CONFLICT);
	}

	@Test
	public void testAddUrl() {
		User user = userRepository.save(new User("user-a"));

		HttpHeaders headers = getHeaders();
		HttpEntity<String> httpEntity = new HttpEntity<>("{\"url\": \"http://www.google.com\"}", headers);

		String url = "/users/" + user.getId() + "/urls";
		ResponseEntity<Url> responseEntityCreated = restTemplate.exchange(url, POST, httpEntity, Url.class);
		assertThat(responseEntityCreated.getStatusCode()).isEqualTo(CREATED);

		long urlId = responseEntityCreated.getBody().getId();
		assertThat(urlRepository.existsById(urlId)).isTrue();

	}

	@Test
	public void testDelete() {
		User user = userRepository.save(new User("user-a"));
		assertThat(userRepository.count()).isEqualTo(1);

		restTemplate.delete("/user/" + user.getId());
		assertThat(userRepository.count()).isEqualTo(0);
	}
	

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
