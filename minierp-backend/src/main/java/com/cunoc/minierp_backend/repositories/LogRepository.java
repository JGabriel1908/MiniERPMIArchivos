package com.cunoc.minierp_backend.repositories;

import com.cunoc.minierp_backend.models.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

    List<Log> findAllByOrderByFechaDesc(Pageable pageable);

    List<Log> findByModuloOrderByFechaDesc(String modulo, Pageable pageable);
}
