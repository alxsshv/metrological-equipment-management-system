package main.repository;

import main.model.MiTypeInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiTypeInstructionRepository extends JpaRepository<MiTypeInstruction, Long> {

}
