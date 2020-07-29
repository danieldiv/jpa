package com.algaworks.sistemacursos;

import com.algaworks.sistemacursos.model.Aluno;
import com.algaworks.sistemacursos.model.Curso;
import com.algaworks.sistemacursos.model.Modulo;
import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;

public class Relacionamentos {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Cursos-PU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //gravacaoEmCascata(entityManager);
        //gravacaoEmCascataUpdate(entityManager);
        //gravacaoEmCascataManyToMany(entityManager);
        //exclusaoCascata(entityManager);
        //exclusaoEmCascataComManyToMany(entityManager);
        exclusaoEmCascataRemovendoOrfaos(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void gravacaoEmCascata(EntityManager entityManager) {
        Curso curso = new Curso();
        curso.setNome("Especialista Spring REST");

        Modulo modulo = new Modulo();
        modulo.setNome("Introdução ao Spring");
        modulo.setCurso(curso);

        curso.setModulos(Arrays.asList(modulo));

        entityManager.getTransaction().begin();
        entityManager.persist(curso);
        //entityManager.persist(modulo);
        entityManager.getTransaction().commit();
    }

    public static void gravacaoEmCascataUpdate(EntityManager entityManager) {
        Curso curso = new Curso();
        curso.setId(1);
        curso.setNome("Especialista JPA");

        Modulo modulo = new Modulo();
        modulo.setId(1);
        modulo.setNome("Nome alterado");
        modulo.setCurso(curso);

        curso.setModulos(Arrays.asList(modulo));

        entityManager.getTransaction().begin();
        entityManager.merge(curso);
        entityManager.getTransaction().commit();
    }

    public static void gravacaoEmCascataManyToMany(EntityManager entityManager) {
        Curso curso = new Curso();
        curso.setNome("Especialista Spring REST");

        Aluno aluno = new Aluno();
        aluno.setNome("Maria Carla");

        curso.setAlunos(Arrays.asList(aluno));

        entityManager.getTransaction().begin();
        entityManager.persist(curso);
        //entityManager.persist(aluno);
        entityManager.getTransaction().commit();
    }

    public static void exclusaoCascata(EntityManager entityManager) {
        Modulo modulo = entityManager.find(Modulo.class, 1);

        entityManager.getTransaction().begin();

        //modulo.getAulas().forEach(a -> entityManager.remove(a));
        entityManager.remove(modulo);

        entityManager.getTransaction().commit();
    }

    public static void exclusaoEmCascataComManyToMany(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Curso curso = entityManager.find(Curso.class, 1);
        curso.getAlunos().remove(0);
        entityManager.merge(curso);

        entityManager.getTransaction().commit();
    }

    public static void exclusaoEmCascataRemovendoOrfaos(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Modulo modulo = entityManager.find(Modulo.class, 1);
        //modulo.getAulas().remove(0);
        entityManager.remove(modulo);

        entityManager.getTransaction().commit();
    }
}
