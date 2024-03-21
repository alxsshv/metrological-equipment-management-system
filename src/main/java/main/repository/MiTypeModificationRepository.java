package main.repository;

import main.model.MiTypeModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiTypeModificationRepository extends JpaRepository<MiTypeModification, Integer> {
}
