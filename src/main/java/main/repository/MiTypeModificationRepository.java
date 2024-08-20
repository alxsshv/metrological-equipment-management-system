package main.repository;

import main.model.MiTypeDetails;
import main.model.MiTypeModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiTypeModificationRepository extends JpaRepository<MiTypeModification, Long> {
   List<MiTypeModification> findByMiTypeDetails(MiTypeDetails miTypeDetails);
   List<MiTypeModification> findByMiTypeDetailsAndNotationIgnoreCaseContaining(MiTypeDetails miTypeDetails, String notation);
}
