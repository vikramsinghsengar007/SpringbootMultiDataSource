package net.codejava.second.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.codejava.second.entity.SecondProduct;

@Repository
public interface SecondaryRepository extends JpaRepository<SecondProduct, Long> {
}
