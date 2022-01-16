package com.kpi.diploma.controller;

import com.kpi.diploma.controller.technical.Pager;
import com.kpi.diploma.domain.Car;
import com.kpi.diploma.domain.user.Trip;
import com.kpi.diploma.domain.user.User;
import com.kpi.diploma.dto.CreateCarDto;
import com.kpi.diploma.dto.CreateTripDto;
import com.kpi.diploma.service.base.CarService;
import com.kpi.diploma.service.base.TripService;
import com.kpi.diploma.service.base.UserService;

import com.kpi.diploma.service.co2.CO2AmountService;
import com.kpi.diploma.validator.NewCarValidator;
import com.kpi.diploma.validator.NewTripValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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

    @GetMapping({"/home", "/"})
    public String home(Model model) {
        model.addAttribute("user", userService.obtainCurrentPrincipleUser());
        return "user/home";
    }


    /**
     * @param pageSize - the size of the page
     * @param page     - the page number
     * @return model and view
     */
    @GetMapping("/cars")
    public String listAllCars(Model model,
                              @RequestParam("pageSize") Optional<Integer> pageSize,
                              @RequestParam("page") Optional<Integer> page) {

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        User user = userService.obtainCurrentPrincipleUser();
        Page<Car> cars = carService.findAllByUserPageable(user, PageRequest.of(evalPage, evalPageSize));
        Pager pager = new Pager(cars.getTotalPages(), cars.getNumber(), BUTTONS_TO_SHOW);

        model.addAttribute("cars", cars);
        model.addAttribute("selectedPageSize", evalPageSize);
        model.addAttribute("pageSizes", PAGE_SIZES);
        model.addAttribute("pager", pager);
        return "user/cars";
    }

    @GetMapping("/trips")
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

    @GetMapping("/new-car")
    public String formNewCar(Model model) {
        model.addAttribute("carForm", new CreateCarDto());
        model.addAttribute("user", userService.obtainCurrentPrincipleUser());
        return "/user/new-car";
    }

    @PostMapping("/cars")
    public String createNewCar(@ModelAttribute("carForm") CreateCarDto dto,
                               BindingResult bindingResult, Model model) {

        newCarValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("form had errors.");
            model.addAttribute("user", userService.obtainCurrentPrincipleUser());
            return "user/new-car";
        }
        carService.createNewCar(dto);
        return "redirect:/user/cars";
    }

    @GetMapping("/new-trip")
    public String formNewTrip(Model model) {
        model.addAttribute("tripForm", new CreateCarDto());
        model.addAttribute("user", userService.obtainCurrentPrincipleUser());
        return "/user/new-trip";
    }

    @ResponseBody
    @PostMapping("/calc-CO2")
    public Map<String, Object> calcCO2(@RequestBody CreateTripDto dto, BindingResult bindingResult) {
        newTripValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("form had errors.");
            return Map.of("success", false);
        }
        double calculatedCO2ForTrip = co2AmountService.calculateCO2ForTrip(dto);
        if (dto.isSaveToHistory()){
            tripService.createNewTrip(dto, calculatedCO2ForTrip);
        }
        return Map.of("success", true, "amount", calculatedCO2ForTrip);
    }

    @DeleteMapping("/cars/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return "redirect:/user/cars";
    }

}
