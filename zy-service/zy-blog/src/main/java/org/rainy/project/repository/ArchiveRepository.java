package org.rainy.project.repository;

import org.rainy.project.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Integer> {
}
