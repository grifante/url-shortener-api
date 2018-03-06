package com.urlshortener.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.urlshortener.model.Stats;
import com.urlshortener.model.Url;
import com.urlshortener.model.User;

public class StatsControllerTest extends BaseControllerTest {

	@Test
	public void testGetStatsGlobal() {
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

		ResponseEntity<Stats> response = restTemplate.getForEntity("/stats", Stats.class);
		Stats stats = response.getBody();

		assertThat(stats.getHits()).isEqualTo(5);
		assertThat(stats.getUrlCount()).isEqualTo(2);
		assertThat(stats.getTopUrls()).hasSize(2);
	}

	@Test
	public void testGetStatsGlobalEmpty() {
		ResponseEntity<Stats> response = restTemplate.getForEntity("/stats", Stats.class);
		Stats stats = response.getBody();

		assertThat(stats.getHits()).isEqualTo(0);
		assertThat(stats.getUrlCount()).isEqualTo(0);
		assertThat(stats.getTopUrls()).isEmpty();
	}

	@Test
	public void testGetStatsUrl() {
		User user = userRepository.save(new User("user-a"));
		Url urlBase = new Url();
		urlBase.setUserId(user.getId());
		urlBase.setHits(5);
		urlBase.setUrl("http://www.google.com");
		urlBase.setShortUrl("http://localhost:8181/PrNuQZB");
		urlBase = urlRepository.save(urlBase);

		ResponseEntity<Url> response = restTemplate.getForEntity("/stats/" + urlBase.getId(), Url.class);
		Url url = response.getBody();
		assertThat(url.getHits()).isEqualTo(5);
		assertThat(url.getUrl()).isEqualTo(url.getUrl());
		assertThat(url.getShortUrl()).isEqualTo(url.getShortUrl());
	}

	@Test
	public void testGetStatsUrlNotFound() {
		ResponseEntity<Url> response = restTemplate.getForEntity("/stats/1", Url.class);
		assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
	}

}
