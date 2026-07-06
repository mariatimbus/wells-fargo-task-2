package com.wellsfargo.counselor.repository;

import com.wellsfargo.counselor.entity.Advisor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvisorRepository extends CrudRepository<Advisor, Long> {
}
