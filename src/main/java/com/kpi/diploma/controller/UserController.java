package com.kpi.diploma.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kpi.diploma.controller.technical.Pager;
import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.Trip;
import com.kpi.diploma.domain.type.FuelType;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.CreateCarDto;
import com.kpi.diploma.dto.CreateTripDto;
import com.kpi.diploma.dto.TestTripDto;
import com.kpi.diploma.service.base.CarService;
import com.kpi.diploma.service.base.TripService;
import com.kpi.diploma.service.base.UserService;
import com.kpi.diploma.service.co2.CO2AmountService;
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

    @GetMapping({"/cabinet","/home", "/"})
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

        //        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        //        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        User user = userService.obtainCurrentPrincipleUser();
        //        Page<Car> cars = carService.findAllByUserPageable(user, PageRequest.of(evalPage, evalPageSize));
        //        Pager pager = new Pager(cars.getTotalPages(), cars.getNumber(), BUTTONS_TO_SHOW);
        //        model.addAttribute("pager", pager);
        //		model.addAttribute("selectedPageSize", evalPageSize);
        //		model.addAttribute("pageSizes", PAGE_SIZES);

        List<Car> cars = carService.findAllByUser(user);
        model.addAttribute("cars", cars);

        return "user/cars";
    }

    @GetMapping("/trip/history")
    public String listAllTrips(Model model,
                               @RequestParam("pageSize") Optional<Integer> pageSize,
                               @RequestParam("page") Optional<Integer> page) {

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        User user = userService.obtainCurrentPrincipleUser();
        Page<Trip> trips = tripService.findAllByUserPageable(user, PageRequest.of(evalPage, evalPageSize));
        Pager pager = new Pager(trips.getTotalPages(), trips.getNumber(), BUTTONS_TO_SHOW);

        model.addAttribute("trips", trips);
        model.addAttribute("selectedPageSize", evalPageSize);
        model.addAttribute("pageSizes", PAGE_SIZES);
        model.addAttribute("pager", pager);
        return "user/trips";
    }

    @GetMapping("/car/new")
    public String formNewCar(Model model) {
        model.addAttribute("carForm", new CreateCarDto());
        model.addAttribute("user", userService.obtainCurrentPrincipleUser());
        model.addAttribute("fuelTypes", FuelType.values());
        return "/user/new-car";
    }

    @PostMapping("/car")
    public String createNewCar(@ModelAttribute("carForm") CreateCarDto dto,
                               BindingResult bindingResult, Model model) {

        newCarValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("form had errors.");
            model.addAttribute("carForm", new CreateCarDto());
            model.addAttribute("user", userService.obtainCurrentPrincipleUser());
            model.addAttribute("fuelTypes", FuelType.values());
            return "user/new-car";
        }
        carService.createNewCar(dto);
        return "redirect:/user/car";
    }

    @GetMapping("/trip/new")
    public String formNewTrip(Model model) {
        User user = userService.obtainCurrentPrincipleUser();
        List<Car> cars = carService.findAllByUser(user);
        model.addAttribute("tripForm", new CreateCarDto());
        model.addAttribute("user", user);
        model.addAttribute("cars", cars);
        return "/user/new-trip";
    }

    @GetMapping("/trip/test")
    public String formTestTrip(Model model) {
        model.addAttribute("tripForm", new TestTripDto());
        model.addAttribute("user", userService.obtainCurrentPrincipleUser());
        model.addAttribute("fuelTypes", FuelType.values());
        return "/user/test-trip";
    }

    @ResponseBody
    @PostMapping(value = "/co2",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> calcCO2(@RequestBody CreateTripDto dto, BindingResult bindingResult) {
        newTripValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("form had errors.");
            return Map.of("success", false);
        }
        double calculatedCO2ForTrip = co2AmountService.calculateCO2ForTrip(dto);
        if (dto.isSaveToHistory()) {
            tripService.createNewTrip(dto, calculatedCO2ForTrip);
        }
        return Map.of("success", true, "amount", calculatedCO2ForTrip);
    }

    @DeleteMapping("/car/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return "redirect:/user/car";
    }

}
