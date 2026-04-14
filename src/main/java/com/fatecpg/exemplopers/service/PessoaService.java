package com.fatecpg.exemplopers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fatecpg.exemplopers.model.ExperienciaProfissional;
import com.fatecpg.exemplopers.model.Funcionario;
import com.fatecpg.exemplopers.model.Pessoa;
import com.fatecpg.exemplopers.model.Promocao;
import com.fatecpg.exemplopers.repository.ExperienciaProfissionalRepository;
import com.fatecpg.exemplopers.repository.PessoaRepository;
import com.fatecpg.exemplopers.repository.PromocaoRepository;

@Service
public class PessoaService {
    
    private final PessoaRepository pessoaRepository;
    private final ExperienciaProfissionalRepository experienciaProfissionalRepository;
    private final PromocaoRepository promocaoRepository;

    public PessoaService(PessoaRepository pessoaRepository, ExperienciaProfissionalRepository experienciaProfissionalRepository, PromocaoRepository promocaoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.experienciaProfissionalRepository = experienciaProfissionalRepository;
        this.promocaoRepository = promocaoRepository;
    }

    public Pessoa salvarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> updatePessoa(Long id, Pessoa newPessoaData) {
        return pessoaRepository.findById(id).map(pessoa -> {
            pessoa.setNome(newPessoaData.getNome());
            return pessoaRepository.save(pessoa);
        });
    }

    public boolean deletePessoa(Long id) {
        if (pessoaRepository.existsById(id)) {
            pessoaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Funcionario> findFuncionarioById(Long id) {
        Optional<Funcionario> funcionarioOpt = pessoaRepository.findById(id) // procura a pessoa pelo ID
            .filter(p -> p instanceof Funcionario) // verifica se a pessoa é um Funcionario
            .map(p -> (Funcionario) p); // converte a pessoa para Funcionario ou retorna Optional.empty() se não for um Funcionario
        return funcionarioOpt;
    }

    public Optional<ExperienciaProfissional> updateExperienciaProfissional(Long funcionarioId, Long experienciaId, ExperienciaProfissional novoep) {
        Optional<ExperienciaProfissional> ep = 
            experienciaProfissionalRepository.findByIdAndFuncionarioId(experienciaId, funcionarioId)
            .map(existingEp -> {
                existingEp.setDescricao(novoep.getDescricao());
                existingEp.setCargaHoraria(novoep.getCargaHoraria());
                return experienciaProfissionalRepository.save(existingEp);
            });
        return ep;
    }

    public Optional<Promocao> updatePromocao(Long funcionarioId, Long promocaoId, Promocao novopro) {
        Optional<Promocao> pro = 
            promocaoRepository.findByIdAndFuncionarioId(promocaoId, funcionarioId)
            .map(existingPro -> {
                existingPro.setDataPromocao(novopro.getDataPromocao());
                existingPro.setNovoSalario(novopro.getNovoSalario());
                return promocaoRepository.save(existingPro);
            });
        return pro;
    }
}
