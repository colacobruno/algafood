package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    private List<Estado> listar(){
        return estadoRepository.findAll();
    }

    @GetMapping({"/{estadoId}"})
    public ResponseEntity<Estado> buscar(
            @PathVariable("estadoId") Long estadoId){
        Optional<Estado> estado = estadoRepository.findById(estadoId);

        if (estado.isPresent()){
            return ResponseEntity.ok(estado.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado){
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> atualizar(
            @PathVariable("estadoId") Long estadoId,
            @RequestBody Estado estado){
        Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);

        if(estadoAtual.isPresent()){
            BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
            Estado estadoSalvo = cadastroEstadoService.salvar(estadoAtual.get());
            return ResponseEntity.ok(estadoSalvo);
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(
            @PathVariable("estadoId") Long estadoId){
        try{
            cadastroEstadoService.excluir(estadoId);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

}
