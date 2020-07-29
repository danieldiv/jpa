package com.algaworks.sistemaclientes.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Artigo {

    private Integer id;

    private String titulo;

    @ElementCollection
    @CollectionTable(name = "artigo_tag",
            joinColumns = @JoinColumn(name = "artigo_id"))
    @Column(name = "tag")
    private List<String> tags;
}
