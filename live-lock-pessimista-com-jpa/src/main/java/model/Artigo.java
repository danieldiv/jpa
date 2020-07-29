package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Artigo {

    @Id
    private Integer id;

    private String titulo;
    private String conteudo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artigo artigo = (Artigo) o;
        return id.equals(artigo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
