package com.urlshortener.model;

import java.util.List;

public class Stats {

	private long hits;
	private long urlCount;
	private List<Url> topUrls;

	public Stats() {
	}

	public Stats(long hits, long urlCount, List<Url> topUrls) {
		this.hits = hits;
		this.urlCount = urlCount;
		this.topUrls = topUrls;
	}

	public long getHits() {
		return hits;
	}

	public long getUrlCount() {
		return urlCount;
	}

	public List<Url> getTopUrls() {
		return topUrls;
	}

}
