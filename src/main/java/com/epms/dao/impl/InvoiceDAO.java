package com.epms.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.epms.dao.IInvoiceDAO;
import com.epms.dto.EventVenueDetailsDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class InvoiceDAO  implements IInvoiceDAO{

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public EventVenueDetailsDTO getEventVenueDetails(long eventId) {
		String sql = "SELECT v.venueName" 
						+ " , vt.venueType"  
						+ "	, v.cost" 
						+ "	, et.eventType"  
						+ "	, e.eventTitle"  
						+ "	, DATE_FORMAT(e.startDate, '%d/%m/%Y') as eventStartDate" 
						+ "	, DATE_FORMAT(e.endDate, '%d/%m/%Y') as  eventEndDate" 
						+ "	, u.firstName " 
						+ " , u.lastName " 
						+ "	, u.mobileNumber"  
				+" FROM event e" 
				+" join packagedetails p on e.packageId=p.packageDetailsId " 
				+" join venue v on p.venueId = v.venueId" 
				+" join enuvenuetype vt on v.venueTypeId= vt.enuvenueTypeId" 
				+" join enueventtype et on et.eventTypeId = p.eventTypeId" 
				+" join userdetails u on u.userDetailsId= e.userDetailsId where e.eventId = :eventId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("eventId", eventId);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<EventVenueDetailsDTO>(EventVenueDetailsDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
