package com.urlshortener.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.junit.Test;

import com.urlshortener.model.Stats;
import com.urlshortener.model.Url;
import com.urlshortener.model.User;

public class StatsControllerTest extends BaseControllerTest {

	@Test
	public void testGetStatsGlobal() {
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

		var stats = restTemplate.getForEntity("/stats", Stats.class).getBody();

		assertThat(stats.getHits()).isEqualTo(5);
		assertThat(stats.getUrlCount()).isEqualTo(2);
		assertThat(stats.getTopUrls()).hasSize(2);
	}

	@Test
	public void testGetStatsGlobalEmpty() {
		var stats = restTemplate.getForEntity("/stats", Stats.class).getBody();

		assertThat(stats.getHits()).isEqualTo(0);
		assertThat(stats.getUrlCount()).isEqualTo(0);
		assertThat(stats.getTopUrls()).isEmpty();
	}

	@Test
	public void testGetStatsUrl() {
		var user = userRepository.save(new User("user-a"));
		var urlBase = new Url();
		urlBase.setUserId(user.getId());
		urlBase.setHits(5);
		urlBase.setUrl("http://www.google.com");
		urlBase.setShortUrl("http://localhost:8181/PrNuQZB");
		urlBase = urlRepository.save(urlBase);

		var url = restTemplate.getForEntity("/stats/" + urlBase.getId(), Url.class).getBody();
		assertThat(url.getHits()).isEqualTo(5);
		assertThat(url.getUrl()).isEqualTo(url.getUrl());
		assertThat(url.getShortUrl()).isEqualTo(url.getShortUrl());
	}

	@Test
	public void testGetStatsUrlNotFound() {
		var statusCode = restTemplate.getForEntity("/stats/1", Url.class).getStatusCode();
		assertThat(statusCode).isEqualTo(NOT_FOUND);
	}

}
