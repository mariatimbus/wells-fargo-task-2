package com.wellsfargo.counselor.repository;

import com.wellsfargo.counselor.entity.Portfolio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
}
