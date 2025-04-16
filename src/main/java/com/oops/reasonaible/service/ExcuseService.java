package com.oops.reasonaible.service;

import org.springframework.stereotype.Service;

import com.oops.reasonaible.entity.Excuse;
import com.oops.reasonaible.repository.ExcuseRepository;
import com.oops.reasonaible.service.dto.ExcuseRequest;
import com.oops.reasonaible.service.dto.ExcuseResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExcuseService {

	private final ExcuseRepository excuseRepository;

	public ExcuseResponse createExcuse(ExcuseRequest excuseRequest) {
		Excuse excuse = excuseRequest.toEntity();
		excuseRepository.save(excuse);
		return ExcuseResponse.from(excuse);
		// return excuseRepository.save(excuseRequest.toEntity())
		// 	.map(excuse -> ExcuseResponse.of(excuse.getId(), excuse.getExcuse(), excuse.getModifiedExcuse(),
		// 		excuse.getCreatedAt(), excuse.getUpdatedAt()));
	}

}
