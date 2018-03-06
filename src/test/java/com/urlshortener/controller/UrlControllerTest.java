package com.urlshortener.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.urlshortener.model.Url;
import com.urlshortener.model.User;

public class UrlControllerTest extends BaseControllerTest {

	@Test
	public void testGetUrl() {
		Url url = newUrl();
		ResponseEntity<String> responseOk = restTemplate.getForEntity("/urls/" + url.getId(), String.class);
		assertThat(OK).isEqualTo(responseOk.getStatusCode());
	}

	@Test
	public void testGetUrlNotFound() {
		ResponseEntity<String> responseNotFound = restTemplate.getForEntity("/urls/1", String.class);
		assertThat(NOT_FOUND).isEqualTo(responseNotFound.getStatusCode());
	}

	@Test
	public void testDelete() {
		Url url = newUrl();
		assertThat(urlRepository.count()).isEqualTo(1);

		restTemplate.delete("/urls/" + url.getId());
		assertThat(urlRepository.count()).isEqualTo(0);
	}

	private Url newUrl() {
		User user = userRepository.save(new User("user-a"));
		Url url = new Url();
		url.setUserId(user.getId());
		url.setUrl("http://www.google.com");
		url.setShortUrl("http://localhost:8181/PrNuQZB");
		return urlRepository.save(url);
	}

}
