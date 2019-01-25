package car.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import car.model.Car;
import car.model.CarRepository;

@RestController
@RequestMapping("/car")
public class CarController {
	
	@Autowired
	private CarRepository carRepository;
	
	@GetMapping("/cars")
	public List<Car> getAllCars() {
		return carRepository.findAll();
	}
}
