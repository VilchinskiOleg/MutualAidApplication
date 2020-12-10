package org.tms.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tms.finalproject.entity.Massage;

public interface MassageRepository extends JpaRepository<Massage, Long> {
}
