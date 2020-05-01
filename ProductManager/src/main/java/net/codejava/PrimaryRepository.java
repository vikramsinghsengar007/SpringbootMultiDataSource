package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryRepository extends JpaRepository<Product, Long> {
}
