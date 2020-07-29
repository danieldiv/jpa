package com.algaworks.cadastroprojetos;

import com.algaworks.cadastroprojetos.model.Alocacao;
import com.algaworks.cadastroprojetos.model.AlocacaoId;
import com.algaworks.cadastroprojetos.model.Colaborador;
import com.algaworks.cadastroprojetos.model.Projeto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("Projetos-PU");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Novo Centro Comercial");
        entityManager.persist(projeto);

        Colaborador recurso = new Colaborador();
        recurso.setResponsabilidade("Compras");
        recurso.setCustoHora(BigDecimal.TEN);
        recurso.setNome("Daniel");
        recurso.setEmail("compras@projetocentrocomerical.com");
        entityManager.persist(recurso);

        Alocacao alocacao = new Alocacao();
        alocacao.setId(new AlocacaoId());
        alocacao.setProjeto(projeto);
        alocacao.setRecurso(recurso);
        entityManager.persist(alocacao);

        entityManager.getTransaction().commit();

        entityManager.close();
        factory.close();

    }
}
