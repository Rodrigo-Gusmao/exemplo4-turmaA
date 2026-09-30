package br.com.uam.exemplo2.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.uam.exemplo2.model.Avaliacao;
import br.com.uam.exemplo2.repository.AvaliacaoRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {
    private final AvaliacaoRepository repository;

    public AvaliacaoController(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody @Valid Avaliacao avaliacao) {

        Avaliacao avaliacaoSalvo = repository.save(avaliacao);

        URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(avaliacaoSalvo.getId())
                            .toUri();

        return ResponseEntity.created(location).body(avaliacaoSalvo);
    }

    @GetMapping
    public List<Avaliacao> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        Optional<Avaliacao> avalicaoBuscado = repository.findById(id);

        if (avalicaoBuscado.isPresent()) {
            return ResponseEntity.ok(avalicaoBuscado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizarAvaliacao(@PathVariable Long id, 
                                                    @RequestBody @Valid Avaliacao avaliacaoAtualizado) {

        Optional<Avaliacao> avalicaoBuscado = repository.findById(id);

        if (avalicaoBuscado.isPresent()) {
            Avaliacao avaliacaoExistente = avalicaoBuscado.get();
            avaliacaoExistente.setAutor(avaliacaoAtualizado.getAutor());
            avaliacaoExistente.setComentario(avaliacaoAtualizado.getComentario());
            avaliacaoExistente.setNota(avaliacaoAtualizado.getNota());
            avaliacaoExistente.setProduto(avaliacaoAtualizado.getProduto());

            Avaliacao avaliacaoSalvo = repository.save(avaliacaoExistente);
            return ResponseEntity.ok(avaliacaoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
