package com.fatecpg.exemplopers.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity  // Indica que esta classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados.
@Table(name = "promocao") // Especifica o nome da tabela no banco de dados que esta entidade irá mapear.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Promocao {

    @Id // Indica que o campo "id" é a chave primária da entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Especifica que o valor do campo "id" será gerado automaticamente
    @Column(nullable = false) // Especifica que a coluna "id" é obrigatória (não pode ser nula)
    @ToString.Include
    @EqualsAndHashCode.Include
    private long id;

    @Column(nullable = false) 
    @ToString.Include
    private LocalDate dataPromocao;

    @Column(nullable = false) // Especifica que a coluna "nome" é obrigatória e única (sem repetições)
    @ToString.Include
    private BigDecimal novoSalario;

    @JsonBackReference
    @ManyToOne // promoção de um funcionário
    @JoinColumn(name = "funcionario_id") // Nome da coluna no banco
    private Funcionario funcionario;  // corresponde ao mappedBy em Funcionário

}