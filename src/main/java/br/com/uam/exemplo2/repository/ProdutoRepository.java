package br.com.uam.exemplo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uam.exemplo2.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
