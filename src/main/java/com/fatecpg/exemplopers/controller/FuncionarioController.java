package com.fatecpg.exemplopers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatecpg.exemplopers.model.ExperienciaProfissional;
import com.fatecpg.exemplopers.model.Funcionario;
import com.fatecpg.exemplopers.model.Pessoa;
import com.fatecpg.exemplopers.model.Promocao;
import com.fatecpg.exemplopers.repository.ExperienciaProfissionalRepository;
import com.fatecpg.exemplopers.service.PessoaService;

@RestController
@RequestMapping("/meusite")
public class FuncionarioController {
    private final PessoaService pessoaService;

    public FuncionarioController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
    
    @PostMapping("/funcionarios")
    public ResponseEntity<Funcionario> postMethodName(@RequestBody Funcionario funcionario) {
        Funcionario funcionarioCriado = (Funcionario)pessoaService.salvarPessoa(funcionario);
        return ResponseEntity.ok(funcionarioCriado);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<Funcionario>> allFuncionarios() {
        List<Funcionario> funcionarios = pessoaService.getAllPessoas()
            .stream().filter(p -> p instanceof Funcionario).map(p -> (Funcionario) p).toList();
        return ResponseEntity.ok(funcionarios);
    }
    
    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<Funcionario> updateUser(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        return pessoaService.updatePessoa(id, funcionario)
                .map(p -> ResponseEntity.ok((Funcionario)p))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        boolean deleted = pessoaService.deletePessoa(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    
    @PostMapping("/funcionarios/{id}/experienciaProfissional")
    public ResponseEntity<Funcionario> addExperienciaProfissional(@PathVariable Long id, @RequestBody ExperienciaProfissional ep) {
        Optional<Pessoa> funcionario = pessoaService.findFuncionarioById(id)
            .map(f -> {
                f.addExperienciaProfissional(ep);
                return pessoaService.updatePessoa(id, f);
            })
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return ResponseEntity.ok((Funcionario)funcionario.get());
    }

    @PutMapping("/funcionarios/{id}/experienciaProfissional/{epid}")
    public ResponseEntity<ExperienciaProfissional> updateExperienciaProfissional(
        @PathVariable Long id, 
        @PathVariable Long epid,
        @RequestBody ExperienciaProfissional ep) 
    {
        return pessoaService.updateExperienciaProfissional(id, epid, ep)
            .map(xep -> ResponseEntity.ok(xep))
            .orElse(ResponseEntity.notFound().build());
    }

    // Requisição POST. {id} é o id fo funcionário que terá objeto Promocao adicionado.
    @PostMapping("/funcionarios/{id}/promocao")
    public ResponseEntity<Funcionario> addPromocao(
            @PathVariable Long id,        //id do funcionário 
            @RequestBody Promocao pro) {  //objeto Promoção proveniente do Cliente
        Optional<Pessoa> funcionario = 
            pessoaService.findFuncionarioById(id) // busca funcionario pelo id
            .map(f -> {                   // processa funcionario se encontrado
                f.addPromocao(pro);
                f.setSalario(pro.getNovoSalario());
                return pessoaService.updatePessoa(id, f);
            })
            .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        // retorna JSON com os dados do funcionário com promoção adicionada
        return ResponseEntity.ok((Funcionario)funcionario.get());
    }

    // Atualiza promoção - {id} é o id do usuário, {proid} é o id da Promocao
    @PutMapping("/funcionarios/{id}/promocao/{proid}")
    public ResponseEntity<Promocao> updatePromocao(
        @PathVariable Long id,     // id do funcionario
        @PathVariable Long proid,  // id da Promocao
        @RequestBody Promocao pro) // Promocao com novos dados
    {
        return pessoaService.updatePromocao(id, proid, pro)
            .map(xpro -> ResponseEntity.ok(xpro))
            .orElse(ResponseEntity.notFound().build());
    }

}
