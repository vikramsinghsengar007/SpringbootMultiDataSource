package net.codejava;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import net.codejava.entity.AllProductsDTO;
import net.codejava.first.entity.Product;
import net.codejava.second.entity.SecondProduct;

@Controller
public class AppController {

	@Autowired
	private ProductService service;

	@GetMapping("/")
	public String viewHomePage(Model model) {
//		List<SecondProduct> listProducts = service.listAll();
		//List<Product> listProducts = service.listAllPrimary();
		 List<AllProductsDTO> listProducts = service.listFromAllDB();
		model.addAttribute("listProducts", listProducts);

		return "index";
	}

	@GetMapping("/new")
	public String showNewProductPage(Model model) {
		Product product = new Product();
		SecondProduct sproduct = new SecondProduct();
		model.addAttribute("product", sproduct);

		return "new_product";
	}

	@PostMapping(value = "/save")
	public String saveProduct(@ModelAttribute("product") SecondProduct product) {
		service.save(product);

		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Optional<SecondProduct> product = service.get(id);
		mav.addObject("product", product);

		return mav;
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";
	}
}
