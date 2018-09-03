package com.urlshortener.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Url {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	private long hits;
	private String url;
	private String shortUrl;
	private String userId;

}
