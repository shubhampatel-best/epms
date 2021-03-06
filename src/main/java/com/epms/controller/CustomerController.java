package com.epms.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epms.authentication.CustomUserDetailsDTO;
import com.epms.dto.AddressDTO;
import com.epms.dto.ContactUsDTO;
import com.epms.dto.EnquiryDTO;
import com.epms.dto.EnuCityDTO;
import com.epms.dto.EnuStateDTO;
import com.epms.dto.EventBannerDTO;
import com.epms.dto.EventDTO;
import com.epms.dto.PackageDetailsDTO;
import com.epms.dto.PackageServiceProviderMappingDTO;
import com.epms.dto.ServiceProviderDTO;
import com.epms.dto.UserDetailsDTO;
import com.epms.dto.VenueDTO;
import com.epms.dto.VenueEventTypeMappingDTO;
import com.epms.dto.VenueFacilityMappingDTO;
import com.epms.dto.VenueImageMappingDTO;
import com.epms.email.configuration.IMailService;
import com.epms.email.configuration.Mail;
import com.epms.service.IAddressService;
import com.epms.service.IEmployeeService;
import com.epms.service.IEnquiryService;
import com.epms.service.IEnuCityService;
import com.epms.service.IEnuCountryService;
import com.epms.service.IEnuEmployeeRoleService;
import com.epms.service.IEnuEmployeeWorkingStatusService;
import com.epms.service.IEnuEnquiryStatusService;
import com.epms.service.IEnuEventStatusService;
import com.epms.service.IEnuEventTypeService;
import com.epms.service.IEnuServiceProviderWorkingStatusService;
import com.epms.service.IEnuServiceTypeService;
import com.epms.service.IEnuStateService;
import com.epms.service.IEnuVenueFacilityService;
import com.epms.service.IEnuVenueTypeService;
import com.epms.service.IEventBannerService;
import com.epms.service.IEventEmployeeMappingService;
import com.epms.service.IEventService;
import com.epms.service.IFeedbackService;
import com.epms.service.IInvoiceService;
import com.epms.service.IPackageDetailsService;
import com.epms.service.IPackageServiceProviderMappingService;
import com.epms.service.IServiceProviderService;
import com.epms.service.IUserDetailsService;
import com.epms.service.IVenueEventTypeMappingService;
import com.epms.service.IVenueFacilityMappingService;
import com.epms.service.IVenueImageMappingService;
import com.epms.service.IVenueService;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class CustomerController {
	@Autowired
	IEnuCityService enuCityService;

	@Autowired
	IEnquiryService enquiryService;

	@Autowired
	IEnuStateService enuStateService;

	@Autowired
	IEnuCountryService enuCountryService;

	@Autowired
	IUserDetailsService userDetailsService;

	@Autowired
	IAddressService addressService;

	@Autowired
	IMailService mailService;

	@Autowired
	IPackageDetailsService packageDetailsService;

	@Autowired
	IServiceProviderService serviceProviderService;

	@Autowired
	IEnuServiceTypeService enuServiceTypeService;

	@Autowired
	IPackageServiceProviderMappingService packageServiceProviderMappingService;

	@Autowired
	IVenueService venueService;

	@Autowired
	IEnuEventTypeService enuEventTypeService;

	@Autowired
	IEnuVenueFacilityService enuVenueFacilityService;

	@Autowired
	IEnuVenueTypeService enuVenueTypeService;

	@Autowired
	IVenueFacilityMappingService venueFacilityMappingService;

	@Autowired
	IVenueEventTypeMappingService venueEventTypeMappingService;

	@Autowired
	IVenueImageMappingService venueImageMappingService;

	@Autowired
	Environment env;

	@Autowired
	IInvoiceService invoiceService;

	@Autowired
	IEnuServiceTypeService enuserviceTypeService;

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	IEnuEmployeeRoleService enuEmployeeRoleService;

	@Autowired
	IEventService eventService;

	@Autowired
	IEnuEnquiryStatusService enquiryStatusService;

	@Autowired
	IEnuEventStatusService enuEventStatusService;

	@Autowired
	IEventBannerService eventBannerService;

	@Autowired
	IEventEmployeeMappingService eventEmployeeMappingService;

	@Autowired
	IEnuEmployeeWorkingStatusService enuEmployeeWorkingStatusService;

	@Autowired
	IEnuServiceProviderWorkingStatusService enuServiceProviderWorkingStatusService;

	@Autowired
	IFeedbackService feedbackService;

	@GetMapping("home")
	public ModelAndView homePage() {
		ModelAndView modelandmap = new ModelAndView("index");
		modelandmap.addObject("eventNames",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		return modelandmap;
	}

	@GetMapping("getStates/{countryId}")
	public List<EnuStateDTO> getStates(@PathVariable @NotNull Integer countryId) {
		log.info("Loading states in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("countryId", countryId);
		return enuStateService.findByNamedParameters(paramSource);
	}

	@GetMapping("getCities/{stateId}")
	public List<EnuCityDTO> getCities(@PathVariable @NotNull Integer stateId) {
		log.info("Loading cities in registration page");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("stateId", stateId);
		return enuCityService.findByNamedParameters(paramSource);
	}

	@GetMapping("customer-registration")
	public ModelAndView loadCustomerRegistrationPage() {
		log.info("Load customer registration page");
		final ModelAndView modelandmap = new ModelAndView("customerRegisteration");
		modelandmap.addObject("countries", enuCountryService.findAll());
		modelandmap.addObject("userDetailsDTO", new UserDetailsDTO());
		modelandmap.addObject("addressDTO", new AddressDTO());
		return modelandmap;
	}

	public BindingResult checkCustomerResults(@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO,
			BindingResult userResult) {
		// Valid Email
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(userDetailsDTO.getEmail());
		boolean isValidEmail = matcher.find();
		if (isValidEmail == false) {
			userResult.addError(new FieldError("userDetailsDTO", "email", "Please enter valid email address."));
		}

		// Unique Email
		List<UserDetailsDTO> emailDTO = userDetailsService.isUniqueEmail(userDetailsDTO.getEmail());
		if (emailDTO.isEmpty() != true) {
			if (emailDTO.get(0).getIsActive() == true) {
				userResult.addError(new FieldError("userDetailsDTO", "email", "Email address is already registered."));
			} else {
				userResult.addError(new FieldError("userDetailsDTO", "email",
						"Email address is already registered and account is deactivated.\nPlease contact us to active it."));
			}
		}

		// Password Rules
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		boolean isValidPassword = userDetailsDTO.getPassword().matches(pattern);
		if (isValidPassword == false) {
			userResult.addError(
					new FieldError("userDetailsDTO", "password", "Please enter password according to rules."));
		}

		// 10 digit Mobile Number
		if (userDetailsDTO.getMobileNumber().length() != 10) {
			userResult
					.addError(new FieldError("userDetailsDTO", "mobileNumber", "Please enter 10 digit mobile number."));
		}

		// Unique Mobile Number
		List<UserDetailsDTO> mobileNumberDTO = userDetailsService
				.isUniqueMobileNumber(userDetailsDTO.getMobileNumber());
		if (mobileNumberDTO.isEmpty() != true) {
			if (mobileNumberDTO.get(0).getIsActive() == true) {
				userResult.addError(
						new FieldError("userDetailsDTO", "mobileNumber", "Mobile Number is already registered."));
			} else {
				userResult.addError(new FieldError("userDetailsDTO", "mobileNumber",
						"Mobile Number is already registered and account is deactivated.\nPlease contact us to active it."));
			}
		}

		return userResult;
	}

	@PostMapping("customer-registration")
	public ModelAndView submitCustomerRegistration(
			@Valid @ModelAttribute("userDetailsDTO") UserDetailsDTO userDetailsDTO, BindingResult userResult,
			@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO, BindingResult addressResult,
			ModelAndView modelandmap, RedirectAttributes rm) {
		userResult = checkCustomerResults(userDetailsDTO, userResult);
		if (userResult.hasErrors() == true) {
			modelandmap = new ModelAndView("customerRegisteration");
			modelandmap.addObject("userDetailsDTO", userDetailsDTO);
			modelandmap.addObject("countries", enuCountryService.findAll());
			modelandmap.addObject("addressDTO", addressDTO);
			MapSqlParameterSource paramSourceCountry = new MapSqlParameterSource();
			paramSourceCountry.addValue("countryId", addressDTO.getCountryId());
			modelandmap.addObject("states", enuStateService.findByNamedParameters(paramSourceCountry));

			MapSqlParameterSource paramSourceState = new MapSqlParameterSource();
			paramSourceState.addValue("stateId", addressDTO.getStateId());
			modelandmap.addObject("cities", enuCityService.findByNamedParameters(paramSourceState));
			return modelandmap;
		}

		String password = userDetailsDTO.getPassword();
		AddressDTO insertAddressDTO = addressService.insert(addressDTO);
		userDetailsDTO.setAddressId(insertAddressDTO.getAddressId());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(userDetailsDTO.getPassword());
		userDetailsDTO.setPassword(encodedPassword);
		UserDetailsDTO insertUserDetailsDTO = userDetailsService.insert(userDetailsDTO);

		modelandmap = new ModelAndView("redirect:/customer/index");
		rm.addFlashAttribute("username", insertUserDetailsDTO.getEmail());
		rm.addFlashAttribute("password", password);
		return modelandmap;
	}

	@GetMapping("login")
	public ModelAndView viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("login");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getRoleName().equalsIgnoreCase("ROLE_CUSTOMER")) {
				return new ModelAndView("redirect:/customer/index"); // customer index
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
				return new ModelAndView("redirect:/admin/dashboard");
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_SERVICEPROVIDER")) {
				return new ModelAndView("redirect:/serviceprovider/dashboard");
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_EMPLOYEE")) {
				return new ModelAndView("redirect:/employee/dashboard");
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
				return new ModelAndView("redirect:/employee/dashboard");
				// return new ModelAndView("redirect:/eventorganizer/dashboard");
			}
		}
		return new ModelAndView("index");
	}

	@GetMapping("logout")
	public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/login");
		return mv;
	}

	@GetMapping("getUpcomingEvents/{eventTypeId}")
	public ModelAndView getUpcomingEvents(@PathVariable long eventTypeId) {
		ModelAndView modelandmap = new ModelAndView();

		List<EventDTO> events;
		long statusId = DataAccessUtils
				.singleResult(enuEventStatusService
						.findByNamedParameters(new MapSqlParameterSource().addValue("status", "Verified")))
				.getStatusId();
		if (eventTypeId == -1) {
			events = eventService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)
					.addValue("eventStatusId", statusId).addValue("isPublic", true));
		} else {
			events = eventService.findByNamedParameters(
					new MapSqlParameterSource().addValue("isActive", true).addValue("eventStatusId", statusId)
							.addValue("isPublic", true).addValue("eventTypeId", eventTypeId));
		}

		if (events.size() == 0) {
			modelandmap.setViewName("fragments :: noEvents");
			return modelandmap;
		} else {
			modelandmap.setViewName("fragments :: upcomingEvents");
		}

		List<EventBannerDTO> banners = events.stream().map(event -> {
			List<EventBannerDTO> tempBanner = eventBannerService.findByNamedParameters(
					new MapSqlParameterSource().addValue("isActive", true).addValue("eventID", event.getEventId()));
			if (tempBanner.size() == 0) {
				return null;
			} else {
				return tempBanner.get(0);
			}
		}).collect(Collectors.toList());

		List<String> venueAddresses = events.stream().map(event -> {
			VenueDTO tempVenueDTO = venueService.findById(
					packageDetailsService.findById(event.getPackageId().longValue()).getVenueId().longValue());
			return tempVenueDTO.getVenueName() + ", "
					+ getAddress(addressService.findById(tempVenueDTO.getAddressId().longValue()));
		}).collect(Collectors.toList());

		List<String> eventTypes = events.stream().map(event -> {
			return DataAccessUtils
					.singleResult(enuEventTypeService.findByNamedParameters(
							new MapSqlParameterSource().addValue("eventTypeId", event.getEventTypeId())))
					.getEventType();
		}).collect(Collectors.toList());

		List<UserDetailsDTO> customers = events.stream().map(event -> {
			return DataAccessUtils.singleResult(userDetailsService.findByNamedParameters(
					new MapSqlParameterSource().addValue("userDetailsId", event.getUserDetailsId())));
		}).collect(Collectors.toList());

		modelandmap.addObject("events", events);
		modelandmap.addObject("banners", banners);
		modelandmap.addObject("venueAddresses", venueAddresses);
		modelandmap.addObject("eventTypes", eventTypes);
		modelandmap.addObject("customers", customers);

		return modelandmap;
	}

	@GetMapping("events")
	public ModelAndView events() {
		final ModelAndView modelandmap = new ModelAndView("events");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			modelandmap.addObject("layoutPage", "_layout");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getIsCustomer() == true) {
				modelandmap.addObject("layoutPage", "customer/_layout");
			} else {
				modelandmap.addObject("layoutPage", "_layout");
			}
		}
		modelandmap.addObject("eventNames",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		return modelandmap;
	}

	@GetMapping("inquiry")
	public ModelAndView inquiry() {
		final ModelAndView modelandmap = new ModelAndView("enquiry");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			modelandmap.addObject("layoutPage", "_layout");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getIsCustomer() == true) {
				modelandmap.addObject("layoutPage", "customer/_layout");
			} else {
				modelandmap.addObject("layoutPage", "_layout");
			}
		}

		modelandmap.addObject("eventTypes",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("enquiry", new EnquiryDTO());
		return modelandmap;
	}

	@PostMapping("inquiry")
	public ModelAndView addEnquiry(@Valid @ModelAttribute("enquiry") EnquiryDTO enquiry, BindingResult result) {
		// Valid Email
//		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
//		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
//				Pattern.CASE_INSENSITIVE);
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(enquiry.getEmail());
		boolean isValidEmail = matcher.find();
		if (isValidEmail == false) {
			result.addError(new FieldError("enquiry", "email", "Please enter valid email address."));
		}

		if (result.hasErrors() == true) {
			final ModelAndView modelandmap = new ModelAndView("enquiry");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
				modelandmap.addObject("layoutPage", "_layout");
			} else {
				CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
				if (userDetails.getIsCustomer() == true) {
					modelandmap.addObject("layoutPage", "customer/_layout");
				} else {
					modelandmap.addObject("layoutPage", "_layout");
				}
			}

			enquiry.setStartDate(null);
			enquiry.setEndDate(null);
			modelandmap.addObject("eventTypes",
					enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
			modelandmap.addObject("enquiry", enquiry);
			return modelandmap;
		}

		EnquiryDTO enquiryDTO = enquiryService.insert(enquiry);
		Mail mail = new Mail();
		mail.setMailTo(enquiryDTO.getEmail());
		mail.setMailSubject("Enquiry Submitted");
		mail.setContentType("text/html");
		String content = "<b>Thanks for being awesome! Your Inquiry number: Inquiry#" + enquiryDTO.getEnquiryId()
				+ "</b> <br/><br/>"
				+ "We have received your message and would like to thank you for writing to us. If your inquiry is urgent, "
				+ "please use the telephone number listed in website to talk to one of our staff members. "
				+ "<br/> Otherwise, we will reply by email as soon as possible." + "<br/>Talk to you soon<br/>, Unico";
		mail.setMailContent(content);
		mailService.sendEmail(mail);
		return new ModelAndView("redirect:/home");
	}

	@GetMapping("getVenuesOnEventType/{venueId}")
	public ModelAndView getVenuesOnEventType(ModelAndView modelandmap, @PathVariable long venueId) {
		List<VenueDTO> venueDTOs;
		if (venueId == -1) {
			venueDTOs = venueService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true));
			modelandmap.setViewName("fragments :: resultsListVenue");
		} else {
			venueDTOs = venueService.findByNamedParameters(
					new MapSqlParameterSource().addValue("isActive", true).addValue("venueTypeId", venueId));
		}

		if (venueDTOs.size() == 0) {
			modelandmap.setViewName("fragments :: resultsListVenue1");
		} else {
			modelandmap.setViewName("fragments :: resultsListVenue");
		}

		List<String> addresses = venueDTOs.stream().map(venueDTO -> {
			return getAddress(addressService.findById(venueDTO.getAddressId().longValue()));
		}).collect(Collectors.toList());

		List<String> venueTypes = venueDTOs.stream().map(venueDTO -> {
			return enuVenueTypeService.findById(venueDTO.getVenueTypeId().longValue()).getVenueType();
		}).collect(Collectors.toList());

		List<List<VenueFacilityMappingDTO>> venueFacilities = venueDTOs.stream().map(venueDTO -> {
			return venueFacilityMappingService.findByNamedParameters(
					new MapSqlParameterSource().addValue("venueId", venueDTO.getVenueId().longValue()));
		}).collect(Collectors.toList());

		List<List<VenueEventTypeMappingDTO>> venueEventTypes = venueDTOs.stream().map(venueDTO -> {
			return venueEventTypeMappingService.findByNamedParameters(
					new MapSqlParameterSource().addValue("venueId", venueDTO.getVenueId().longValue()));
		}).collect(Collectors.toList());

		List<Map<Integer, List<VenueImageMappingDTO>>> venueImages = venueDTOs.stream().map(venueDTO -> {
			Map<Integer, List<VenueImageMappingDTO>> temp = new HashMap<>();
			temp.put(venueDTO.getVenueId(), venueImageMappingService.findByNamedParameters(
					new MapSqlParameterSource().addValue("venueId", venueDTO.getVenueId().longValue())));
			return temp;
		}).collect(Collectors.toList());

		// System.out.println(venueImages);
		modelandmap.addObject("venueDTOs", venueDTOs);
		modelandmap.addObject("addresses", addresses);
		modelandmap.addObject("venueTypes", venueTypes);
		modelandmap.addObject("venueFacilities", venueFacilities);
		modelandmap.addObject("venueEventTypes", venueEventTypes);
		modelandmap.addObject("eventNames",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("venueImages", venueImages);
		return modelandmap;
	}

	@GetMapping("venue")
	public ModelAndView gallery() {
		final ModelAndView modelandmap = new ModelAndView("venue");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			modelandmap.addObject("layoutPage", "_layout");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getIsCustomer() == true) {
				modelandmap.addObject("layoutPage", "customer/_layout");
			} else {
				modelandmap.addObject("layoutPage", "_layout");
			}
		}
		modelandmap.addObject("venueTypes",
				enuVenueTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		return modelandmap;
	}

	public String getAddress(AddressDTO addressDTO) {
		String address;
		address = addressDTO.getAddress1();
		if (addressDTO.getAddress2() != null) {
			address += ", " + addressDTO.getAddress2();
		}
		address += ",\n " + enuCityService.findById(addressDTO.getCityId().longValue()).getCity() + ", "
				+ enuStateService.findById(addressDTO.getStateId().longValue()).getState() + ", "
				+ enuCountryService.findById(addressDTO.getCountryId().longValue()).getCountry() + " - "
				+ addressDTO.getPostalCode();
		return address;
	}

	@GetMapping("getPackagesOfEvent/{eventTypeId}")
	public ModelAndView packagesByEventType(ModelAndView modelandmap, @PathVariable long eventTypeId) {
		List<PackageDetailsDTO> packageDetailsDTO;
		if (eventTypeId == -1) {
			packageDetailsDTO = packageDetailsService.findByNamedParameters(
					new MapSqlParameterSource().addValue("isActive", true).addValue("isStatic", true));
			modelandmap.setViewName("fragments :: resultsList");
		} else {
			packageDetailsDTO = packageDetailsService.findByNamedParameters(new MapSqlParameterSource()
					.addValue("isActive", true).addValue("eventTypeId", eventTypeId).addValue("isStatic", true));
		}

		if (packageDetailsDTO.size() == 0) {
			modelandmap.setViewName("fragments :: resultsList1");
		} else {
			modelandmap.setViewName("fragments :: resultsList");
		}

//		List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
//				.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true));

		List<String> venueDetails = packageDetailsDTO.stream().map(packageDTO -> {
			return venueService.findById(packageDTO.getVenueId().longValue()).getVenueName() + ", "
					+ getAddress(addressService.findById(
							venueService.findById(packageDTO.getVenueId().longValue()).getAddressId().longValue()));
		}).collect(Collectors.toList());

		List<String> eventType = packageDetailsDTO.stream().map(packageDTO -> {
			return enuEventTypeService.findById(packageDTO.getEventTypeId().longValue()).getEventType();
		}).collect(Collectors.toList());

		List<Map<String, String>> serviceWithProviders = packageDetailsDTO.stream().map(item -> {
			Map<String, String> t = new HashMap<String, String>();
			List<PackageServiceProviderMappingDTO> packageServiceProviderMappings = packageServiceProviderMappingService
					.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true).addValue("packageId",
							item.getPackageDetailsId()));
			packageServiceProviderMappings.stream().map(item2 -> {
				ServiceProviderDTO temp = serviceProviderService.findById(item2.getServiceProviderId().longValue());
				return t.put(enuServiceTypeService.findById(temp.getServiceTypeId().longValue()).getService(),
						userDetailsService.findById(temp.getUserDetailsId().longValue()).getServiceProviderName());
			}).collect(Collectors.toList());
			return t;
		}).collect(Collectors.toList());

		modelandmap.addObject("venueDetails", venueDetails);
		modelandmap.addObject("eventNames",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		modelandmap.addObject("eventTypes", eventType);
		modelandmap.addObject("packageDetailsDTOs", packageDetailsDTO);
		modelandmap.addObject("serviceWithProviders", serviceWithProviders);
		// modelandmap.setViewName("fragments :: resultsList");
		return modelandmap;
	}

	@GetMapping("packages")
	public ModelAndView packages() {
		final ModelAndView modelandmap = new ModelAndView("packages");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			modelandmap.addObject("layoutPage", "_layout");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getIsCustomer() == true) {
				modelandmap.addObject("layoutPage", "customer/_layout");
			} else {
				modelandmap.addObject("layoutPage", "_layout");
			}
		}
		modelandmap.addObject("eventNames",
				enuEventTypeService.findByNamedParameters(new MapSqlParameterSource().addValue("isActive", true)));
		return modelandmap;
	}

	@GetMapping("forgot-password")
	public ModelAndView showForgotPasswordForm() {
		return new ModelAndView("forgotPassword");
	}

	@PostMapping("forgot-password")
	public ModelAndView processForgotPassword(HttpServletRequest request, ModelAndView model, RedirectAttributes rm) {

		String email = request.getParameter("email");

		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("email", email);
		parameterSource.addValue("isActive", true);
		UserDetailsDTO userDetailsDTO = DataAccessUtils
				.singleResult(userDetailsService.findByNamedParameters(parameterSource));
		if (userDetailsDTO != null) {
			try {
				String token = UUID.randomUUID().toString();
				userDetailsService.updateResetPasswordToken(token, email);
				// Control Panel\System and Security\Windows Defender Firewall\Customize
				// Settings
				String resetPasswordLink = getSiteURL(request) + "/reset-password?token=" + token;
				Mail mail = new Mail();
				mail.setMailTo(email);
				mail.setMailSubject("Here's the link to reset your password");
				mail.setContentType("text/html");
				mail.setMailContent("<p>Hi " + userDetailsDTO.getFirstName() + " " + userDetailsDTO.getLastName()
						+ ",</p>" + "<p>You have requested to reset your password.</p>"
						+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + resetPasswordLink
						+ "\">Change my password</a></p>"
						+ "<p>The above link will be expired in 5 minutes. Ignore this email if you do remember your password, "
						+ "or you have not made the request.</p>");
				// log.debug("resetPasswordLink: {}",resetPasswordLink);
				mailService.sendEmail(mail);
				rm.addFlashAttribute("message", "We have sent a reset password link to your email. Please check.");
			} catch (Exception e) {
				log.error("Exception :  {}", e);
				rm.addFlashAttribute("error", "Error while sending email");
			}
		} else {
			rm.addFlashAttribute("error", "Email address is not registered in the system.");
		}
		model.setViewName("redirect:/forgot-password");
		return model;
	}

	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

	@GetMapping("reset-password")
	public ModelAndView showResetPasswordForm(@RequestParam(value = "token") String token, RedirectAttributes rm) {
		ModelAndView model = new ModelAndView("resetPassword");
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("resetPasswordToken", token);
		parameterSource.addValue("isActive", true);
		UserDetailsDTO userDetailsDTO = DataAccessUtils
				.singleResult(userDetailsService.findByNamedParameters(parameterSource));

		if (userDetailsDTO != null && userDetailsDTO.getResetPasswordTokenTime() != null) {
			LocalDateTime now = LocalDateTime.now();
			long minutes = ChronoUnit.MINUTES.between(userDetailsDTO.getResetPasswordTokenTime(), now);
			if (minutes >= 5) {
				model.addObject("error", "Please try again because link is expired.");
			} else {
				model.addObject("token", token);
			}
		} else {
			model.addObject("error", "Invalid Token");
		}
		return model;
	}

	@PostMapping("reset-password")
	public ModelAndView processResetPassword(HttpServletRequest request, ModelAndView model, RedirectAttributes rm) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("resetPasswordToken", token);
		parameterSource.addValue("isActive", true);
		UserDetailsDTO userDetailsDTO = DataAccessUtils
				.singleResult(userDetailsService.findByNamedParameters(parameterSource));

		// Password Rules
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		boolean isValidPassword = password.matches(pattern);
		if (isValidPassword == false) {
			rm.addFlashAttribute("error", "Please enter password according to rules.");
			model.setViewName("redirect:/reset-password?token=" + token);
			return model;
		}

		if (userDetailsDTO == null) {
			rm.addFlashAttribute("error", "Invalid Token");
			model.setViewName("redirect:/reset-password?token=" + token);
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(password);
			userDetailsService.updateUserPassword(userDetailsDTO.getUserDetailsId(), encodedPassword);
			model.setViewName("redirect:/login");
		}

		return model;
	}

	@GetMapping("change-password")
	public ModelAndView showChangePasswordPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getRoleName().equalsIgnoreCase("ROLE_CUSTOMER")) {
				return new ModelAndView("customer/changePassword"); // customer index
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
				return new ModelAndView("admin/changePassword");
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_SERVICEPROVIDER")) {
				return new ModelAndView("serviceprovider/changePassword");
			} else if (userDetails.getRoleName().equalsIgnoreCase("ROLE_EVENTORGANIZER")) {
				ModelAndView modelandmap = new ModelAndView("employee/changePassword");
				modelandmap.addObject("layoutTitle", "Event Organizer");
				return modelandmap;
				// return new ModelAndView("eventorganizer/changePassword");
			} else {
				ModelAndView modelandmap = new ModelAndView("employee/changePassword");
				modelandmap.addObject("layoutTitle", "Employee");
				return modelandmap;
			}
		}
	}

	@PostMapping("change-password")
	public ModelAndView changePassword(HttpServletRequest request, ModelAndView model, RedirectAttributes rm) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("redirect:/login");
		} else {
			// Password Rules
			String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
			boolean isValidPassword = request.getParameter("newPassword").matches(pattern);
			if (isValidPassword == false) {
				rm.addFlashAttribute("error", "Please enter password according to rules.");
				model.setViewName("redirect:/change-password");
				return model;
			}
			
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (!passwordEncoder.matches(request.getParameter("oldPassword"), userDetails.getPassword())) {
				rm.addFlashAttribute("error", "Please enter valid old password");
				model.setViewName("redirect:/change-password");
			} else if (passwordEncoder.matches(request.getParameter("newPassword"), userDetails.getPassword())) {
				rm.addFlashAttribute("error", "Old password cannot be same as new password");
				model.setViewName("redirect:/change-password");
			} else {
				String encodedPassword = passwordEncoder.encode(request.getParameter("newPassword"));
				userDetailsService.updateUserPassword(userDetails.getUserDetailsId(), encodedPassword);
				rm.addFlashAttribute("message", "your password is successfully changed.");
				return new ModelAndView("redirect:/logout");
			}
		}
		return model;
	}

	@GetMapping("about-us")
	public ModelAndView showAboutUSPage() {
		ModelAndView modelandmap = new ModelAndView("aboutus");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			modelandmap.addObject("layoutPage", "_layout");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getIsCustomer() == true) {
				modelandmap.addObject("layoutPage", "customer/_layout");
			} else if (userDetails.getIsServiceProvider() == true) {
				modelandmap.addObject("layoutPage", "serviceprovider/_layout");
			} else if (userDetails.getRole().equals("ROLE_EVENTORGANIZER")) {
				modelandmap.addObject("layoutPage", "employee/_layout");
				modelandmap.addObject("layoutTitle", "Event Organizer");
				// modelandmap.addObject("layoutPage", "eventorganizer/_layout");
			} else if (userDetails.getIsEmployee() == true) {
				modelandmap.addObject("layoutPage", "employee/_layout");
				modelandmap.addObject("layoutTitle", "Employee");
			} else {
				modelandmap.addObject("layoutPage", "_layout");
			}
		}
		return modelandmap;
	}

	@GetMapping("contact-us")
	public ModelAndView showContactUSPage() {
		ModelAndView modelandmap = new ModelAndView("contactus");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			modelandmap.addObject("layoutPage", "_layout");
		} else {
			CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
			if (userDetails.getIsCustomer() == true) {
				modelandmap.addObject("layoutPage", "customer/_layout");
			} else if (userDetails.getIsServiceProvider() == true) {
				modelandmap.addObject("layoutPage", "serviceprovider/_layout");
			} else if (userDetails.getRole().equals("ROLE_EVENTORGANIZER")) {
				modelandmap.addObject("layoutTitle", "Event Organizer");
				modelandmap.addObject("layoutPage", "employee/_layout");
				// modelandmap.addObject("layoutPage", "eventorganizer/_layout");
			} else if (userDetails.getIsEmployee() == true) {
				modelandmap.addObject("layoutPage", "employee/_layout");
				modelandmap.addObject("layoutTitle", "Employee");
			} else {
				modelandmap.addObject("layoutPage", "_layout");
			}
		}
		modelandmap.addObject("contactUs", new ContactUsDTO());
		return modelandmap;
	}

	@PostMapping("contact-us")
	public ModelAndView submitContactDetails(@Valid @ModelAttribute("contactUs") ContactUsDTO contactUsDTO) {
		Mail mail = new Mail();
		mail.setMailTo(env.getProperty("spring.mail.username"));
		mail.setMailSubject("Contact Us");
		mail.setContentType("text/html");
		String content = "<p><strong>Contact Us By: </strong>" + contactUsDTO.getEmail() + "</p>"
				+ "<p><strong>Subject: </strong>" + contactUsDTO.getSubject() + "</p>"
				+ "<p><strong>Message: </strong></p>" + "<p>" + contactUsDTO.getMessage() + "</p>";
		mail.setMailContent(content);
		mailService.sendEmail(mail);
		return new ModelAndView("redirect:/contact-us");
	}
}