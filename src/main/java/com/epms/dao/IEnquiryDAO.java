package com.epms.dao;

import java.util.List;

import com.epms.core.ICRUDRepository;
import com.epms.dto.EnquiryDTO;

public interface IEnquiryDAO extends ICRUDRepository<EnquiryDTO, Long> {

	List<EnquiryDTO> findAllEnquiryWithStatus();

	EnquiryDTO findEnquiryById(Long id);
	public void updateResponse(EnquiryDTO inquiry);
	public void updateInreviewStatus(long enquiryId, Integer statusId);

}
