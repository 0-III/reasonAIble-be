package com.oops.reasonaible.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oops.reasonaible.entity.Excuse;
import com.oops.reasonaible.exception.CommonException;
import com.oops.reasonaible.exception.ErrorCode;
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

	public List<ExcuseResponse> getAllExcuses() {
		return excuseRepository.findAll()
			.stream()
			.map(ExcuseResponse::from)
			.toList();
	}

	public ExcuseResponse getExcuse(Long excuseId) {
		return excuseRepository.findById(excuseId)
			.map(ExcuseResponse::from)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
	}

	public void deleteExcuse(Long excuseId) {
		excuseRepository.deleteById(excuseId);
	}
}
