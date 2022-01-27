package com.epms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epms.dto.AddressDTO;
import com.epms.dto.EnuCityDTO;
import com.epms.dto.EnuStateDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.service.IAddressService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuServiceTypeService;
import com.epms.service.IEnuStateService;
import com.epms.service.IServiceProviderService;
import com.epms.service.IUserDetailsService;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/serviceprovider")
@Slf4j
public class ServiceProviderController {
	@Autowired
	IEnuCityService enuCityService;

	@Autowired
	IEnuStateService enuStateService;

	@Autowired
	IEnuCountryService enuCountryService;

	@Autowired
	IUserDetailsService userDetailsService;

	@Autowired
	IAddressService addressService;
	
	@Autowired
	IEnuServiceTypeService enuServiceTypeService;
	
	@Autowired
	IServiceProviderService serviceProviderService;

	@GetMapping
	public ModelAndView homePage() {
		return new ModelAndView("serviceProvider/index");
	}

	@GetMapping("/getStates/{countryId}")
	public List<EnuStateDTO> getStates(@PathVariable @NotNull Integer countryId) {
		log.info("Loading states in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId", countryId);
		return enuStateService.findByNamedParameters(paramSource);
	}
	
	@GetMapping("/getCities/{stateId}")
	public List<EnuCityDTO> getCities(@PathVariable @NotNull Integer stateId) {
		log.info("Loading cities in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", stateId);
		return enuCityService.findByNamedParameters(paramSource);
	}
	
	@GetMapping("/serviceprovider-registration")
	public ModelAndView loadServiceProviderRegistrationPage() {
		log.info("Load serviceprovider registration page");
		final ModelAndView modelandmap = new ModelAndView("serviceProviderRegistration");
		modelandmap.addObject("serviceTypes", enuServiceTypeService.findAllActive());
		modelandmap.addObject("countries", enuCountryService.findAll());
		modelandmap.addObject("serviceProviderDTO", new ServiceProviderDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}

	@PostMapping("/serviceprovider-registration")
	public ModelAndView submitServiceProviderRegistration(@Valid @ModelAttribute("serviceProviderDTO")ServiceProviderDTO serviceProviderDTO,
			@Valid @ModelAttribute("addressDTO")AddressDTO addressDTO) {
		AddressDTO insertAddressDTO = addressService.insert(addressDTO);
		serviceProviderDTO.setAddressId(insertAddressDTO.getAddressId());
		ServiceProviderDTO insertServiceProviderDTODTO = serviceProviderService.insert(serviceProviderDTO);
		final ModelAndView modelandmap = new ModelAndView("serviceProvider/_layout_serviceProvider");
		modelandmap.addObject("serviceProviderDTO", insertServiceProviderDTODTO);
		return modelandmap;
	}
}