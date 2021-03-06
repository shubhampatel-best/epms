package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IFeedbackDAO;
import com.epms.dto.FeedbackDTO;
import com.epms.service.IFeedbackService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FeedbackService implements IFeedbackService {
	@Autowired
	private IFeedbackDAO feedbackDAO;

	@Override
	public List<FeedbackDTO> findAll() {
		return feedbackDAO.findAll();
	}

	@Override
	public FeedbackDTO findById(Long id) {
		return feedbackDAO.findById(id);
	}

	@Override
	public List<FeedbackDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return feedbackDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<FeedbackDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return feedbackDAO.findByNamedParameters(paramSource);
	}

	@Override
	public FeedbackDTO insert(FeedbackDTO FeedbackDTO) {
		return feedbackDAO.insert(FeedbackDTO);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public FeedbackDTO update(FeedbackDTO entity) {
		return feedbackDAO.update(entity);
	}

}