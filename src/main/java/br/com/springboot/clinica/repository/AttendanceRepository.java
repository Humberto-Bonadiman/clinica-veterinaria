package br.com.springboot.clinica.repository;

import br.com.springboot.clinica.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {}
