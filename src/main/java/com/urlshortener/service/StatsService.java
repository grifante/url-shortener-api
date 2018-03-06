package com.urlshortener.service;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlshortener.model.Stats;
import com.urlshortener.model.Url;
import com.urlshortener.repository.UrlRepository;

@Service
public class StatsService {

	private static final int TOP_URLS_LIMIT = 10;

	@Autowired
	private UrlRepository repository;

	public Stats getStats(String userId) {
		return getStats(() -> repository.getStats(userId), limit -> repository.findTopUrls(limit, userId));
	}

	public Stats getStats() {
		return getStats(repository::getStats, repository::findTopUrls);
	}

	private Stats getStats(Supplier<Object[]> querySummary, Function<Integer, List<Url>> queryTopUrls) {
		Object[] statsResult = (Object[]) querySummary.get()[0];
		long totalUrls = Long.parseLong(statsResult[0].toString());
		long hits = Long.parseLong(statsResult[1].toString());
		List<Url> topUrls = totalUrls > 0 ? queryTopUrls.apply(TOP_URLS_LIMIT) : Collections.emptyList();
		return new Stats(hits, totalUrls, topUrls);
	}
}
