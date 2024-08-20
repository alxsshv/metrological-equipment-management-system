package main.repository;

import main.model.MiTypeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiTypeDetailsRepository extends JpaRepository<MiTypeDetails, Long> {

}
