package com.algaworks.cadastroprojetos.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//cria uma tabela com todas as informacoes em recurso projeto e suas classes extendidas
//@Inheritance(strategy = InheritanceType.JOINED)//cria uma tabela para cada entidade com suas informacoes
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)//coloca as informacoes do recurso projeto nas classes que o extende
@DiscriminatorColumn(name = "tipo_recurso ")
public abstract class RecursoProjeto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private BigDecimal custoHora;

    private String responsabilidade;
}
