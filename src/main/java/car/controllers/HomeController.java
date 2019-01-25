package car.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import car.model.Car;
import car.model.CarRepository;
import car.services.JokeService;

@Controller
public class HomeController {

	private CarRepository carRepository;
	private JokeService jokeService;
	
	@Autowired	
	public HomeController(JokeService jokeService, CarRepository carRepository) {
		this.carRepository = carRepository;
		this.jokeService = jokeService;
	}
	
	private void blobToImage(Blob blob, String fileName) {
		try(InputStream is = blob.getBinaryStream()) {				
			BufferedImage bimg = ImageIO.read(is);
			
			File input = new File("src/main/resources/static/images/" + fileName + ".png");
			
			ImageIO.write(bimg, "png", input);
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}	
	}
	
	private void blobToImage(Blob blob) {
		blobToImage(blob, "img" + new Random().nextInt(10000) + ".png");
	}
	
	private List<Car> getSliderCars() {
		List<Car> cars = carRepository.findAll();
		
		int rand = new Random().nextInt(cars.size());
		
		List<Car> sliderCars = cars.subList(rand, (rand + 3) > cars.size() ? 
																cars.size() : (rand + 3));
		
		for (Car sliderCar : sliderCars) {
			blobToImage(sliderCar.getImage().getContent(), 
						sliderCar.getModel() + sliderCar.getId());
		}
		
		return sliderCars;
	}
	
	private List<Car> getHotCars() {
		List<Car> cars = carRepository.findAll().stream();
		
		
		
		return null;
	}
	
	@RequestMapping("/")
	String showJoke(Model model) throws SQLException, IOException {
		
		model.addAttribute("sliderCars", getSliderCars().toArray());
		return "home";
	}
	
	@RequestMapping("/car/{carId}")
	String carDetails(Model model, @PathVariable("carId") Long carId) {
		
		Car car = carRepository.getOne(carId);
		
		model.addAttribute("carInfo", car);
		return "carDetails";
	}
}
