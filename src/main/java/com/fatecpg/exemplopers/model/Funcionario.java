package com.fatecpg.exemplopers.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
// Especifica o valor que será usado na coluna "tipo_pessoa" para identificar esta subclasse
@DiscriminatorValue("FUNCIONARIO") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Funcionario extends Pessoa {
    
    @ToString.Include
    @Column(length=50, nullable = false)
    private String cargo;

    @ToString.Include
    @Column(nullable = false)
    private BigDecimal salario;

    @JsonManagedReference
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienciaProfissional> experienciaProfissional = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Promocao> promocao = new ArrayList<>();

    @JsonIgnore
    public void addExperienciaProfissional(ExperienciaProfissional ep) {
        this.experienciaProfissional.add(ep);
        ep.setFuncionario(this);
    }

    @JsonIgnore
    public void addPromocao(Promocao pro) {
        this.promocao.add(pro);
        pro.setFuncionario(this);
    }
}
