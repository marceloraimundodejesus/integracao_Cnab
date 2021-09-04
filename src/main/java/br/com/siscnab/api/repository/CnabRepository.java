package br.com.siscnab.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.siscnab.api.model.Cnab;

public interface CnabRepository extends JpaRepository<Cnab, Long> {

}
