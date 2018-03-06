package com.urlshortener.controller;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.urlshortener.UrlShortenerApplication;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = UrlShortenerApplication.class)
public abstract class BaseControllerTest {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected UrlRepository urlRepository;

	@Autowired
	protected TestRestTemplate restTemplate;

	@Before
	public void init() {
		userRepository.deleteAll();
	}

}
