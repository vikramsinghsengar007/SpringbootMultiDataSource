package net.codejava;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	  @Transactional(transactionManager="SecondaryTransactionManager")
	public List<SecondProduct> listAll() {
		return secondaryRepo.findAll();
	}
	  @Transactional(transactionManager="transactionManager")
		public List<Product> listAllPrimary() {
			return  primaryRepo.findAll();
		}
	  @Transactional(transactionManager="SecondaryTransactionManager")
	public void save(SecondProduct product) {
		secondaryRepo.save(product);
	}
	  @Transactional(transactionManager="SecondaryTransactionManager")
	public Optional<SecondProduct> get(long id) {
		return secondaryRepo.findById(id);
	}
	  @Transactional(transactionManager="SecondaryTransactionManager")
	public void delete(long id) {
		//primaryRepo.deleteById(id);
		secondaryRepo.deleteById(id);
	}
	  
	  @Transactional(value="chainedTransactionManager")
		public List<AllProducts> listFromAllDB() {
		  List l2 =  primaryRepo.findAll();
			List l1 =  secondaryRepo.findAll();
			l1.addAll(l2);
			return l1;
		}
}
