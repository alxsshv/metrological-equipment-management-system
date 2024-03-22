package main.repository;

import main.model.MiType;
import main.model.MiTypeModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiTypeModificationRepository extends JpaRepository<MiTypeModification, Integer> {
   List<MiTypeModification> findByMiType(MiType miType);
}
