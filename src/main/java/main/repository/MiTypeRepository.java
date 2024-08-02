package main.repository;

import lombok.NonNull;
import main.model.MiType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiTypeRepository extends JpaRepository<MiType, Long> {
    Page<MiType> findByNumberContainingOrTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(String number,String title, String notation, Pageable pageable);
    List<MiType> findByNumberContainingOrTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(String number, String title, String notation);
    MiType findByNumber(String number);
    @Override
    @NonNull
    Page<MiType> findAll(@NonNull Pageable pageable);
}
