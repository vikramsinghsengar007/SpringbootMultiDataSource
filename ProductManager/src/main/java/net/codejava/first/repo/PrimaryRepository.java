package net.codejava.first.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.first.entity.Product;

@Repository
public interface PrimaryRepository extends JpaRepository<Product, Long> {
	
	
	
}
