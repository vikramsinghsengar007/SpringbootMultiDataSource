package net.codejava;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.codejava.entity.AllProductsDTO;
import net.codejava.first.entity.Product;
import net.codejava.first.repo.PrimaryRepository;
import net.codejava.second.entity.SecondProduct;
import net.codejava.second.repo.SecondaryRepository;

@Service
@Transactional
public class ProductService {

	@Autowired
	private PrimaryRepository primaryRepo;
	@Autowired
	private SecondaryRepository secondaryRepo;

	@Autowired
	ProductService(PrimaryRepository primaryRepo, SecondaryRepository secondaryRepo) {
		this.primaryRepo = primaryRepo;
		this.secondaryRepo = secondaryRepo;
	}

	@Transactional(transactionManager = "secondTransactionManager")
	public List<SecondProduct> listAll() {
		return secondaryRepo.findAll();
	}

	@Transactional(transactionManager = "firstTransactionManager")
	public List<Product> listAllPrimary() {
		return primaryRepo.findAll();
	}

	@Transactional(transactionManager = "secondTransactionManager")
	public void save(SecondProduct product) {
		secondaryRepo.save(product);
	}

	@Transactional(transactionManager = "secondTransactionManager")
	public Optional<SecondProduct> get(long id) {
		return secondaryRepo.findById(id);
	}

	@Transactional(transactionManager = "secondTransactionManager")
	public void delete(long id) {
		// primaryRepo.deleteById(id);
		secondaryRepo.deleteById(id);
	}

	@Transactional(value = "chainedTransactionManager")
	public List<AllProductsDTO> listFromAllDB() {
		List l2 = primaryRepo.findAll();
		List l1 = secondaryRepo.findAll();
		l1.addAll(l2);
		return l1;
	}
}
