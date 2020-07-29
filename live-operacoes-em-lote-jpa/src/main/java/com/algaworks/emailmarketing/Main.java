package com.algaworks.emailmarketing;

import com.algaworks.emailmarketing.model.Contato;
import com.algaworks.emailmarketing.model.Envio;
import com.algaworks.emailmarketing.model.Mensagem;
import com.algaworks.emailmarketing.model.TemperaturaContato;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("Projetos-PU");
        EntityManager entityManager = factory.createEntityManager();

        //atualizacaoEmVariosRegistros(entityManager);
        //atualizacaoEmLote(entityManager);
        //atualizacaoDeVariosRegistrosEmLotecriteria(entityManager);
        //remocaoDeVariosRegistrosEmLote(entityManager);
        //insercaoDeVariosRegistros(entityManager);
        insercaoDeVariosRegistrosEmLote(entityManager);

        entityManager.close();
        factory.close();
    }

    public static void insercaoDeVariosRegistrosEmLote(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        List<Mensagem> mensagens = entityManager
                .createQuery("select m from Mensagem m")
                .getResultList();

        List<Contato> contatos = entityManager
                .createQuery("select c from Contato c")
                .getResultList();

        int limiteInsercoesMemoria = 3;
        int contatodorLimite = 0;

        for (Mensagem mensagem: mensagens) {
            for (Contato contato: contatos) {
                Envio envio = new Envio();
                envio.setMensagem(mensagem);
                envio.setContato(contato);
                envio.setDataEnvio(LocalDateTime.now());
                entityManager.persist(envio);
                contatodorLimite++;

                if (contatodorLimite == limiteInsercoesMemoria) {
                    entityManager.flush();//pega o que esta na memoria e envia para o banco de dados
                    entityManager.clear();//limpa o que esta na memoria do entity maneger
                    System.out.println("-------- flush: ");
                    contatodorLimite = 0;
                }
            }
        }

        mensagens.forEach(mensagem -> {
            contatos.forEach(contato -> {
                Envio envio = new Envio();
                envio.setMensagem(mensagem);
                envio.setContato(contato);
                envio.setDataEnvio(LocalDateTime.now());
                entityManager.persist(envio);

            });
        });
        entityManager.getTransaction().commit();
    }

    public static void insercaoDeVariosRegistros(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        List<Mensagem> mensagens = entityManager
                .createQuery("select m from Mensagem m")
                .getResultList();

        List<Contato> contatos = entityManager
                .createQuery("select c from Contato c")
                .getResultList();

        mensagens.forEach(mensagem -> {
            contatos.forEach(contato -> {
                Envio envio = new Envio();
                envio.setMensagem(mensagem);
                envio.setContato(contato);
                envio.setDataEnvio(LocalDateTime.now());
                entityManager.persist(envio);
            });
        });
        entityManager.getTransaction().commit();
    }

    public static void remocaoDeVariosRegistrosEmLote(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        entityManager
                .createQuery("delete from Contato c")
                .executeUpdate();

        entityManager.getTransaction().commit();

    }

    public static void atualizacaoDeVariosRegistrosEmLotecriteria(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Contato> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Contato.class);
        Root<Contato> root = criteriaUpdate.from(Contato.class);

        criteriaUpdate.set(root.get("temperatura"), TemperaturaContato.MORNO);

        entityManager.createQuery(criteriaUpdate)
                .executeUpdate();

        entityManager.getTransaction().commit();

    }

    public static void atualizacaoEmLote(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        entityManager
                .createQuery("update Contato c set c.temperatura = :temperatura")
                .setParameter("temperatura", TemperaturaContato.FRIO)
                .executeUpdate();

        entityManager.getTransaction().commit();

    }

    public static void atualizacaoEmVariosRegistros(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        List<Contato> contatos = entityManager
                .createQuery("select c from Contato c")
                .getResultList();

        contatos.forEach(c -> c.setTemperatura(TemperaturaContato.FRIO));
        entityManager.getTransaction().commit();

    }
}
