package main.repository;

import lombok.NonNull;
import main.model.MiType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiTypeRepository extends JpaRepository<MiType, Integer> {
    MiType findByNumber(String number);
    Page<MiType> findBy(String number,String title, Pageable pageable);
    @Override
    @NonNull
    Page<MiType> findAll(@NonNull Pageable pageable);
}
