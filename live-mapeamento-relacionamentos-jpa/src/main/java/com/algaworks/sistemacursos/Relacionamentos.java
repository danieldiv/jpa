package com.algaworks.sistemacursos;

import com.algaworks.sistemacursos.model.Curso;
import com.algaworks.sistemacursos.model.Modulo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Relacionamentos {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Cursos-PU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Curso curso = new Curso();
        curso.setNome("Especialista JPA");

        Modulo modulo = new Modulo();
        modulo.setNome("Introducao ao JPA");
        modulo.setCurso(curso);

        entityManager.getTransaction().begin();

        entityManager.persist(curso);
        entityManager.persist(modulo);

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

    }
}
