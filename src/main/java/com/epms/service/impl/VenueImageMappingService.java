package com.epms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epms.dao.IVenueImageMappingDAO;
import com.epms.dto.VenueImageMappingDTO;
import com.epms.service.IVenueImageMappingService;

import groovy.util.logging.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class VenueImageMappingService implements IVenueImageMappingService {
	@Autowired
	private IVenueImageMappingDAO venueImageMappingDAO;

	@Override
	public List<VenueImageMappingDTO> findAll() {
		return venueImageMappingDAO.findAll();
	}

	@Override
	public VenueImageMappingDTO findById(Long id) {
		return venueImageMappingDAO.findById(id);
	}

	@Override
	public List<VenueImageMappingDTO> findByFieldValue(String fieldName, Object fieldValue) {
		return venueImageMappingDAO.findByFieldValue(fieldName, fieldValue);
	}

	@Override
	public List<VenueImageMappingDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		return venueImageMappingDAO.findByNamedParameters(paramSource);
	}

	@Override
	public VenueImageMappingDTO insert(VenueImageMappingDTO VenueImageMappingDTO) {
		return venueImageMappingDAO.insert(VenueImageMappingDTO);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public VenueImageMappingDTO update(VenueImageMappingDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
