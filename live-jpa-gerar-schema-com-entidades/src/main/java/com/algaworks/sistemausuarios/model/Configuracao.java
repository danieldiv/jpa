package com.algaworks.sistemausuarios.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "configuracao")
public class Configuracao {

    @Id
    private Integer id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "usuario_id",
            foreignKey = @ForeignKey(name = "fk_configuracao_usuario"))
    private Usuario usuario;

    @Column(name = "receber_notificacoes",  nullable = false)
    private boolean recebernotificacoes;

    @Column(name = "encerrar_sessao_automaticamente", nullable = false)
    private boolean encerrarSessaoAutomaticamente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isRecebernotificacoes() {
        return recebernotificacoes;
    }

    public void setRecebernotificacoes(boolean recebernotificacoes) {
        this.recebernotificacoes = recebernotificacoes;
    }

    public boolean isEncerrarSessaoAutomaticamente() {
        return encerrarSessaoAutomaticamente;
    }

    public void setEncerrarSessaoAutomaticamente(boolean encerrarSessaoAutomaticamente) {
        this.encerrarSessaoAutomaticamente = encerrarSessaoAutomaticamente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuracao that = (Configuracao) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
