package com.epms.dao;

import com.epms.core.ICRUDRepository;
import com.epms.dto.VenueDTO;

public interface IVenueDAO extends ICRUDRepository<VenueDTO, Long> {
	public void activate(Long id);

	public Integer getCount();
}
