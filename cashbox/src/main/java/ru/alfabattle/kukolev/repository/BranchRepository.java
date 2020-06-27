package ru.alfabattle.kukolev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alfabattle.kukolev.domain.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}
