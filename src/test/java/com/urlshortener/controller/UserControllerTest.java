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

import com.urlshortener.model.Stats;
import com.urlshortener.model.Url;
import com.urlshortener.model.User;

public class UserControllerTest extends BaseControllerTest {

	@Test
	public void testGetStatsUserNotFound() {
		var statusCode = restTemplate.getForEntity("/users/user-a/stats", Stats.class).getStatusCode();
		assertThat(statusCode).isEqualTo(NOT_FOUND);
	}

	@Test
	public void testGetStatsUser() {
		var user = userRepository.save(new User("user-a"));

		var urlA = new Url();
		urlA.setUserId(user.getId());
		urlA.setHits(5);
		urlA.setUrl("http://www.google.com");
		urlA.setShortUrl("http://localhost:8181/PrNuQZB");
		urlRepository.save(urlA);

		var urlB = new Url();
		urlB.setUserId(user.getId());
		urlB.setUrl("http://www.google.com");
		urlB.setShortUrl("http://localhost:8181/PrNuQHJ");
		urlRepository.save(urlB);

		var stats = restTemplate.getForEntity("/users/" + user.getId() + "/stats", Stats.class).getBody();

		assertThat(stats.getHits()).isEqualTo(5);
		assertThat(stats.getUrlCount()).isEqualTo(2);
		assertThat(stats.getTopUrls()).hasSize(2);
	}

	@Test
	public void testAddUser() {
		var httpEntity = new HttpEntity<>("{\"id\": \"uear-a\"}", getHeaders());
		var statusCode = restTemplate.exchange("/users", POST, httpEntity, String.class).getStatusCode();
		assertThat(statusCode).isEqualTo(CREATED);
	}

	@Test
	public void testAddUserConflict() {
		var httpEntity = new HttpEntity<>("{\"id\": \"usear-a\"}", getHeaders());

		var statusCode = restTemplate.exchange("/users", POST, httpEntity, String.class).getStatusCode();
		assertThat(statusCode).isEqualTo(CREATED);

		statusCode = restTemplate.exchange("/users", POST, httpEntity, String.class).getStatusCode();
		assertThat(statusCode).isEqualTo(CONFLICT);
	}

	@Test
	public void testAddUrl() {
		var user = userRepository.save(new User("user-a"));

		var httpEntity = new HttpEntity<>("{\"url\": \"http://www.google.com\"}", getHeaders());
		var url = "/users/" + user.getId() + "/urls";
		var responseEntityCreated = restTemplate.exchange(url, POST, httpEntity, Url.class);
		
		assertThat(responseEntityCreated.getStatusCode()).isEqualTo(CREATED);

		long urlId = responseEntityCreated.getBody().getId();
		assertThat(urlRepository.existsById(urlId)).isTrue();

	}

	@Test
	public void testDelete() {
		var user = userRepository.save(new User("user-a"));
		assertThat(userRepository.count()).isEqualTo(1);

		restTemplate.delete("/user/" + user.getId());
		assertThat(userRepository.count()).isEqualTo(0);
	}
	

	private HttpHeaders getHeaders() {
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
