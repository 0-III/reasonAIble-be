package com.oops.reasonaible.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oops.reasonaible.entity.Excuse;
import com.oops.reasonaible.exception.CommonException;
import com.oops.reasonaible.exception.ErrorCode;
import com.oops.reasonaible.repository.ExcuseRepository;
import com.oops.reasonaible.service.dto.ExcuseCreateRequest;
import com.oops.reasonaible.service.dto.ExcuseCreateUpdateResponse;
import com.oops.reasonaible.service.dto.ExcuseGetResponse;
import com.oops.reasonaible.service.dto.ExcuseUpdateRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExcuseService {

	private final ExcuseRepository excuseRepository;

	@Transactional
	public ExcuseCreateUpdateResponse createExcuse(ExcuseCreateRequest excuseCreateRequest) {
		Excuse excuse = excuseCreateRequest.toEntity();
		excuseRepository.save(excuse);
		return ExcuseCreateUpdateResponse.of(excuse.getId());
		// return excuseRepository.save(excuseRequest.toEntity())
		// 	.map(excuse -> ExcuseResponse.of(excuse.getId(), excuse.getExcuse(), excuse.getModifiedExcuse(),
		// 		excuse.getCreatedAt(), excuse.getUpdatedAt()));
	}

	public List<ExcuseGetResponse> getAllExcuses() {
		return excuseRepository.findAll()
			.stream()
			.map(ExcuseGetResponse::from)
			.toList();
	}

	public ExcuseGetResponse getExcuse(Long excuseId) {
		return excuseRepository.findById(excuseId)
			.map(ExcuseGetResponse::from)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
	}

	@Transactional
	public void deleteExcuse(Long excuseId) {
		excuseRepository.deleteById(excuseId);
	}

	@Transactional
	public ExcuseCreateUpdateResponse updateExcuse(Long excuseId, ExcuseUpdateRequest excuseRequest) {
		Excuse excuse = excuseRepository.findById(excuseId)
			.orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND));
		excuse.update(excuseRequest.modifiedExcuse());
		return ExcuseCreateUpdateResponse.of(excuse.getId());
	}
}
