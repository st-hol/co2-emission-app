package com.kpi.diploma.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kpi.diploma.controller.technical.Pager;
import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.type.FuelType;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.CO2CalculatedDto;
import com.kpi.diploma.dto.DriveTripDto;
import com.kpi.diploma.dto.GarageCarDto;
import com.kpi.diploma.dto.StatsDto;
import com.kpi.diploma.dto.TestTripDto;
import com.kpi.diploma.payload.ErrorDetails;
import com.kpi.diploma.service.base.CarService;
import com.kpi.diploma.service.base.TripService;
import com.kpi.diploma.service.base.UserService;
import com.kpi.diploma.service.co2.CO2AmountService;
import com.kpi.diploma.service.co2.StatsService;
import com.kpi.diploma.util.ImageUtil;
import com.kpi.diploma.validator.NewCarValidator;
import com.kpi.diploma.validator.NewTripValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 3;
	private static final int[] PAGE_SIZES = {3, 5, 10, 15, 20, 30};
	private static final List<FuelType> FUEL_TYPES = FuelType.getValidTypes();

	@Autowired
	private UserService userService;
	@Autowired
	private CarService carService;
	@Autowired
	private TripService tripService;
	@Autowired
	private NewCarValidator newCarValidator;
	@Autowired
	private NewTripValidator newTripValidator;
	@Autowired
	private CO2AmountService co2AmountService;
	@Autowired
	private StatsService statsService;

	@GetMapping({"/cabinet", "/home", "/"})
	public String home(Model model) {
		model.addAttribute("user", userService.obtainCurrentPrincipleUser());
		return "user/cabinet";
	}

	/**
	 * @param pageSize - the size of the page
	 * @param page     - the page number
	 * @return model and view
	 */
	@GetMapping("/car")
	public String listAllCars(Model model,
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
		User user = userService.obtainCurrentPrincipleUser();
		Page<Car> cars = carService.findAllByUserPageable(user, PageRequest.of(evalPage, evalPageSize));
		Pager pager = new Pager(cars.getTotalPages(), cars.getNumber(), BUTTONS_TO_SHOW);
		model.addAttribute("pager", pager);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("cars", cars);
		return "user/cars";
	}

	@GetMapping("/car/{id}/image")
	public void showImage(@PathVariable String id,
			HttpServletResponse response) throws IOException {
		response.setContentType("image/png");
		Car car = carService.findById(Long.parseLong(id));
		InputStream is = new ByteArrayInputStream(ImageUtil.decompressImage(car.getImageData()));
		IOUtils.copy(is, response.getOutputStream());
	}

	@GetMapping("/trip/history")
	public String listAllTrips(Model model) {
		User user = userService.obtainCurrentPrincipleUser();
		List<Trip> trips = tripService.findAllByUser(user);
		model.addAttribute("trips", trips);
		return "user/trips";
	}

	@GetMapping("/car/new")
	public String formNewCar(Model model) {
		model.addAttribute("carForm", new GarageCarDto());
		model.addAttribute("user", userService.obtainCurrentPrincipleUser());
		model.addAttribute("fuelTypes", FUEL_TYPES);
		return "/user/new-car";
	}

	@GetMapping("/car/{id}/edit")
	public String editCar(Model model, @PathVariable("id") Long id) {
		model.addAttribute("id", id);
		model.addAttribute("carForm", new GarageCarDto());
		model.addAttribute("user", userService.obtainCurrentPrincipleUser());
		model.addAttribute("fuelTypes", FUEL_TYPES);
		return "/user/edit-car";
	}

	@PostMapping("/car")
	public String createNewCar(@ModelAttribute("carForm") GarageCarDto dto, @RequestParam("image") MultipartFile file,
			BindingResult bindingResult, Model model) throws IOException {
		if (file != null && file.getContentType() != null && !"image/png".equals(file.getContentType())) {
			bindingResult.rejectValue("about", "IMAGE", "Please provide image in PNG format.");
		}
		newCarValidator.validate(dto, bindingResult);
		if (bindingResult.hasErrors()) {
			log.info("form had errors.");
			model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
			model.addAttribute("carForm", new GarageCarDto());
			model.addAttribute("user", userService.obtainCurrentPrincipleUser());
			model.addAttribute("fuelTypes", FUEL_TYPES);
			return "user/new-car";
		}
		carService.createNewCar(dto, file);
		return "redirect:/user/car";
	}

	@PutMapping("/car")
	public String updateCar(@ModelAttribute("carForm") GarageCarDto dto, @RequestParam("image") MultipartFile file,
			BindingResult bindingResult, Model model) throws IOException {
		if (file != null && file.getContentType() != null && !"image/png".equals(file.getContentType())) {
			bindingResult.rejectValue("about", "IMAGE", "Please provide image in PNG format.");
		}
		newCarValidator.validate(dto, bindingResult);
		if (bindingResult.hasErrors()) {
			log.info("form had errors.");
			model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
			model.addAttribute("carForm", new GarageCarDto());
			model.addAttribute("user", userService.obtainCurrentPrincipleUser());
			model.addAttribute("fuelTypes", FUEL_TYPES);
			return "user/edit-car";
		}
		carService.updateCar(dto, file);
		return "redirect:/user/car";
	}

	@GetMapping("/trip/new")
	public String formNewTrip(Model model) {
		User user = userService.obtainCurrentPrincipleUser();
		List<Car> cars = carService.findAllByUser(user);
		model.addAttribute("tripForm", new GarageCarDto());
		model.addAttribute("user", user);
		model.addAttribute("cars", cars);
		return "/user/new-trip";
	}

	@GetMapping("/trip/test")
	public String formTestTrip(Model model) {
		model.addAttribute("tripForm", new TestTripDto());
		model.addAttribute("user", userService.obtainCurrentPrincipleUser());
		model.addAttribute("fuelTypes", FUEL_TYPES);
		return "/user/test-trip";
	}

	@ResponseBody
	@PostMapping(value = "/co2", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> calcCO2(@RequestBody DriveTripDto dto, BindingResult bindingResult) {
		newTripValidator.validate(dto, bindingResult);
		if (bindingResult.hasErrors()) {
			log.info("form had errors.");
			Map<String, String> details = bindingResult.getAllErrors().stream()
					.collect(Collectors.toMap(objectError -> UUID.randomUUID().toString(), objectError -> objectError.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorDetails("Bad request", HttpStatus.BAD_REQUEST, details));
		}
		double kgCO2ForTrip = co2AmountService.calculateCO2ForTrip(dto);
		if (dto.isSaveToHistory() && !dto.isTestTrip()) {
			User user = userService.obtainCurrentPrincipleUser();
			tripService.createNewTrip(dto, user, kgCO2ForTrip);
		}
		Car car;
		if (dto.isTestTrip()) {
			car = Car.builder()
					.cylinders(dto.getCylinders())
					.name("Test Car")
					.engineSize(dto.getEngineSize())
					.fuelConsumptionComb(dto.getFuelConsumptionComb())
					.build();
		} else {
			car = carService.findById(dto.getCarId());
		}
		return ResponseEntity.ok(CO2CalculatedDto.builder()
				.from(dto.getFrom())
				.to(dto.getTo())
				.carName(car.getName())
				.engineSize(car.getEngineSize())
				.fuelConsumptionComb(car.getFuelConsumptionComb())
				.co2Amount(kgCO2ForTrip)
				.build());
	}

	@DeleteMapping("/car/{id}")
	public String deleteCar(@PathVariable Long id) {
		carService.delete(id);
		return "redirect:/user/car";
	}

	@ResponseBody
	@GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatsDto> getStats() {
		final User user = userService.obtainCurrentPrincipleUser();

		final Map<String, Double> currentMonthDailyExhaust = statsService.calcCurrentMonthDailyExhaust(user);
		final Map<String, Double> emissionsByMonths = statsService.calcEmissionsByMonths(user);
		final Map<String, Double> carUsageFrequencyToPercents = statsService.calcCarUsageFrequencyToPercents(user);

		final StatsDto response = StatsDto.builder()
				.simulationDemoModeEnabled(false)
				.currentMonthDailyExhaust(currentMonthDailyExhaust)
				//line chart
				.currentMonthDailyExhaustMinY(
						currentMonthDailyExhaust.values().stream().mapToInt(x -> Math.toIntExact(Math.round(x))).min().orElse(0))
				.currentMonthDailyExhaustMaxY(
						currentMonthDailyExhaust.values().stream().mapToInt(x -> Math.toIntExact(Math.round(x))).max().orElse(10))
				.currentMonthDailyExhaustHighest(
						currentMonthDailyExhaust.values().stream().mapToDouble(x -> x).max().orElse(0))
				.currentMonthDailyExhaustMedium(
						currentMonthDailyExhaust.values().stream().mapToDouble(x -> x).average().orElse(0))
				.currentMonthDailyExhaustLowest(
						currentMonthDailyExhaust.values().stream().mapToDouble(x -> x).min().orElse(0))
				//bar chart
				.emissionsByMonths(emissionsByMonths)
				.emissionsByMonthsMinY(
						emissionsByMonths.values().stream().mapToInt(x -> Math.toIntExact(Math.round(x))).min().orElse(0))
				.emissionsByMonthsMaxY(
						currentMonthDailyExhaust.values().stream().mapToInt(x -> Math.toIntExact(Math.round(x))).max().orElse(1000))
				//pie chart
				.carUsageFrequencyToPercents(carUsageFrequencyToPercents)
				.build();
		return ResponseEntity.ok(response);
	}

}
