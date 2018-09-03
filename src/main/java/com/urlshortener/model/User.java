package com.urlshortener.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

	@Id
	private String id;

	public User() {
	}

	public User(String id) {
		this.id = id;
	}
}
