import model.Artigo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

public class LockPessimista {

    private static final Integer ID = 1;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Blog-PU");

        //entendendoAsOperacoes(entityManagerFactory);
        //javaEOWorkbench(entityManagerFactory);
        casoMaisPratico(entityManagerFactory);

    }

    private static void casoMaisPratico(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        Runnable runnable1Joao = () -> {
            entityManager1.getTransaction().begin();
            log(1, "Imediatamente antes find.");
            Artigo artigo1 = entityManager1.find(
                    Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
            log(1, "Imediatamente apos find.");

            artigo1.setConteudo("Alteração do João (TH1)");

            log(1, "Esperando 3 segundos...");
            espear(3000);
            log(1, "Espera dos 3 segs terminada.");

            log(1, "Imediatamente antes commit.");
            entityManager1.getTransaction().commit();
            log(1, "Imediatamente depois commit.");
        };

        Runnable runnable2Maria = () -> {
            log(2, "Esperando 100 milis...");
            espear(100);
            log(2, "Espera dos 100 milis terminada.");

            entityManager2.getTransaction().begin();
            log(2, "Imediatamente antes find.");
            Artigo artigo2 = entityManager2.find(
                    Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
            log(2, "Imediatamente apos find.");

            artigo2.setConteudo(artigo2.getConteudo() + " + Alteração do João (TH2)");

            log(2, "Imediatamente antes commit.");
            entityManager2.getTransaction().commit();
            log(2, "Imediatamente depois commit.");
        };

        Thread thread1 = new Thread(runnable1Joao);
        Thread thread2 = new Thread(runnable2Maria);

        thread1.start();
        thread2.start();
    }

    public static void entendendoAsOperacoes(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        //PESSIMISTIC_READ - eu faco a trava, so eu posso comitar, outros usuario podem apenas ler, pois esta destatualizado
        //PESSIMISTIC_WRITE - eu faco a trava, so eu posso comitar e ler, ninguem mais consegue ver ate eu commitar

        entityManager1.getTransaction().begin();
        Artigo artigo1 = entityManager1.find(
                Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
        artigo1.setConteudo("Alteração do João");
        entityManager1.getTransaction().commit();

        entityManager2.getTransaction().begin();
        Artigo artigo2 = entityManager2.find(
                Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
        artigo2.setConteudo(artigo2.getConteudo() + "Alteração da Maria");
        entityManager2.getTransaction().commit();

        //entityManager1.getTransaction().commit();
    }

    public static void javaEOWorkbench(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        log(1, "Imediatamente antes find.");
        Artigo artigo1 = entityManager.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
        log(1, "Imediatamente após find.");

        artigo1.setConteudo("Alteração João (TH1)");

        log(1, "Esperando 15 segundos...");
        espear(15000);
        log(1, "Espera dos 15 segundos terminada.");

        log(1, "Imediatamente antes do commit.");
        entityManager.getTransaction().commit();
        log(1, "Imediatamente após o commit.");
    }

    private static void log(Integer thread, String msg) {
        System.out.println("[THREAD_" + thread + "] " + msg);
    }

    private static void espear(long milesegundos) {
        try {
            Thread.sleep(milesegundos);
        } catch (Exception e) { }
    }

    /*
    select * from artigo where id = 1;
    select * from artigo where id = 1 for share;
    select * from artigo where id = 1 for update;
    update artigo set conteudo = 'Alteração Workbench.' where id = 1;
     */
}
