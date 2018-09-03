package com.urlshortener.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;

import com.urlshortener.model.Url;
import com.urlshortener.model.User;

public class UrlControllerTest extends BaseControllerTest {

	@Test
	public void testGetUrl() {
		var url = newUrl();
		var statusCode = restTemplate.getForEntity("/urls/" + url.getId(), String.class).getStatusCode();
		assertThat(OK).isEqualTo(statusCode);
	}

	@Test
	public void testGetUrlNotFound() {
		var statusCode = restTemplate.getForEntity("/urls/1", String.class).getStatusCode();
		assertThat(NOT_FOUND).isEqualTo(statusCode);
	}

	@Test
	public void testDelete() {
		var url = newUrl();
		
		assertThat(urlRepository.count()).isEqualTo(1);

		restTemplate.delete("/urls/" + url.getId());
		
		assertThat(urlRepository.count()).isEqualTo(0);
	}

	private Url newUrl() {
		var user = userRepository.save(new User("user-a"));
		var url = new Url();
		url.setUserId(user.getId());
		url.setUrl("http://www.google.com");
		url.setShortUrl("http://localhost:8181/PrNuQZB");
		return urlRepository.save(url);
	}

}
