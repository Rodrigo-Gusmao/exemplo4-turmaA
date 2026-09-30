package br.com.uam.exemplo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uam.exemplo2.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
}
