package com.algalworks.cadastroprojetos;

import com.algalworks.cadastroprojetos.model.Alocacao;
import com.algalworks.cadastroprojetos.model.AlocacaoId;
import com.algalworks.cadastroprojetos.model.Funcionario;
import com.algalworks.cadastroprojetos.model.Projeto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Projetos-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Projeto projeto = new Projeto();
        projeto.setNome("Projeto vendas 2020");

        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João da Silva");

        entityManager.getTransaction().begin();
        entityManager.persist(projeto);
        entityManager.persist(funcionario);

        entityManager.flush();

        Alocacao alocacao = new Alocacao();
        alocacao.setId(new AlocacaoId());
        alocacao.setFuncionario(funcionario);
        alocacao.setProjeto(projeto);

        entityManager.persist(alocacao);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
