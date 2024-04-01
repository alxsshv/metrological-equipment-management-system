package main.repository;

import main.model.VerificationIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VerificationIssueRepository extends JpaRepository<VerificationIssue, Long> {

}
