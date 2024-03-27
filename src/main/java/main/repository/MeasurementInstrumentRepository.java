package main.repository;

import lombok.NonNull;
import main.model.MeasurementInstrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementInstrumentRepository extends JpaRepository <MeasurementInstrument, Integer> {
    MeasurementInstrument findByModificationAndSerialNum(String modification, String serialNum);
    Page<MeasurementInstrument> findByModificationContainingOrSerialNumContainingOrInventoryNumContaining(
            String modification,
            String serialNum,
            String inventoryNum,
            Pageable pageable);
    @Override
    @NonNull
    Page<MeasurementInstrument> findAll(@NonNull Pageable pageable);
}
