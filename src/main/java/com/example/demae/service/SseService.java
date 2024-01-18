package com.example.demae.service;

import com.example.demae.entity.Sse;
import com.example.demae.repository.SseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.yaml.snakeyaml.emitter.Emitter;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SseService {
	private final SseRepository sseRepository;
	private static final long DEFAULT_TIMEOUT = 30 * 60 * 1000;


	public SseEmitter createSse(Long userId) {
//		String id = String.valueOf(userId);
		Optional<Sse> findSse = sseRepository.findByUserId(userId);
		if(findSse.isPresent()) {
			sseRepository.delete(findSse.get());
		}

		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		sseRepository.save(new Sse(userId));
//		emitter.onCompletion(() -> userEmitters.remove(id, emitter));
//		emitter.onTimeout(() -> userEmitters.remove(id, emitter));
		return emitter;
	}

}
