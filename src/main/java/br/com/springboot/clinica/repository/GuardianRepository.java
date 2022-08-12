package br.com.springboot.clinica.repository;

import br.com.springboot.clinica.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Long>{}
