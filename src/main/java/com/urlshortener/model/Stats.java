package com.urlshortener.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stats {

	private long hits;
	private long urlCount;
	private List<Url> topUrls;

	public Stats() {
	}

	public Stats(final long hits, final long urlCount,
			final List<Url> topUrls) {
		this.hits = hits;
		this.urlCount = urlCount;
		this.topUrls = topUrls;
	}

}
