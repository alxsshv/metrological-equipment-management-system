package main.repository;

import main.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(String title, String notation);

    Page<Organization> findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(String title, String notation, Pageable pageable);

    Organization findByNotation(String notation);
}
