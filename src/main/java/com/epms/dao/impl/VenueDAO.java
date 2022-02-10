package com.epms.dao.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.epms.dao.IVenueDAO;
import com.epms.dto.VenueDTO;

import groovy.util.logging.Slf4j;

@Repository
@Slf4j
public class VenueDAO implements IVenueDAO {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<VenueDTO> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from venue");
		return jdbcTemplate.query(sql.toString(), new MapSqlParameterSource(),
				new BeanPropertyRowMapper<VenueDTO>(VenueDTO.class));
	}

	@Override
	public List<VenueDTO> findByFieldValue(String fieldName, Object fieldValue) {
		String sql = "select * from venue where :fieldName = :fieldValue";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("fieldName", fieldName);
		namedParameters.addValue("fieldValue", fieldValue);

		return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<VenueDTO>(VenueDTO.class));
	}

	@Override
	public List<VenueDTO> findByNamedParameters(MapSqlParameterSource paramSource) {
		String sql = "select * from venue where 1=1 ";
		for (Entry<String, Object> param : paramSource.getValues().entrySet()) {
			sql += " and " + param.getKey() + " = :" + param.getKey();
		}

		return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<VenueDTO>(VenueDTO.class));
	}

	@Override
	public VenueDTO findById(Long id) {
		String sql = "select * from venue where venueId = :venueId";

		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("venueId", id);

		try {
			return jdbcTemplate.queryForObject(sql, namedParameters,
					new BeanPropertyRowMapper<VenueDTO>(VenueDTO.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public VenueDTO insert(VenueDTO entity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource sc = new MapSqlParameterSource();
		sc.addValue("venueName", entity.getVenueName());
		sc.addValue("description", entity.getDescription());
		sc.addValue("venueTypeId",entity.getVenueTypeId());
		sc.addValue("addressId", entity.getAddressId());
		sc.addValue("email", entity.getEmail());
		sc.addValue("contactNumber", entity.getContactNumber());
		sc.addValue("latitude", entity.getLatitude());
		sc.addValue("longitude", entity.getLongitude());
		sc.addValue("cost", entity.getCost());
		sc.addValue("guestCapacity", entity.getGuestCapacity());

		int i = jdbcTemplate.update(
				"insert into venue(venueName,description,venueTypeId,addressId,email,contactNumber,latitude,longitude,cost,guestCapacity) values(:venueName,:description,:venueTypeId,:addressId,:email,:contactNumber,:latitude,:longitude,:cost,:guestCapacity)",
				sc, keyHolder, new String[] { "venueId" });

		return findById(keyHolder.getKey().longValue());
	}

	@Override
	public void delete(Long id) {
	}

	@Override
	public VenueDTO update(VenueDTO entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
