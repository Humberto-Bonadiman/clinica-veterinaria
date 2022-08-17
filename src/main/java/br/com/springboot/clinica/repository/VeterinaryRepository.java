package br.com.springboot.clinica.repository;

import br.com.springboot.clinica.entity.Veterinary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinaryRepository extends JpaRepository<Veterinary, Long> {}
