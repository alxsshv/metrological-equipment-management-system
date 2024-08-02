package main.repository;


import main.model.MiCharacteristic;
import main.model.MiDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MiCharacteristicRepository extends JpaRepository<MiCharacteristic, Long> {
    MiCharacteristic findByTitleAndMiDetails(String title, MiDetails miDetails);
}
