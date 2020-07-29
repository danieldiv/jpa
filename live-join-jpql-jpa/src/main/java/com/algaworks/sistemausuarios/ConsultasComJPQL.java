package com.algaworks.sistemausuarios;

import com.algaworks.sistemausuarios.dto.UsuarioDTO;
import com.algaworks.sistemausuarios.model.Configuracao;
import com.algaworks.sistemausuarios.model.Dominio;
import com.algaworks.sistemausuarios.model.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ConsultasComJPQL {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Usuarios-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //primeiraConsulta(entityManager);
        //escolhendoORetorno(entityManager);
        //fazendoProjecoes(entityManager);
        //passandoParamentros(entityManager);
        //fazendoJoins(entityManager);
        //fazendoLeftJoing(entityManager);
        //carregamentoComJoinFetch(entityManager);
        //filtrandoRegistros(entityManager);
        //utilizadoOperadoresLogicos(entityManager);
        //utilizadoOperadorIn(entityManager);
        //ordenandoResultados(entityManager);
        paginandoResultados(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void paginandoResultados(EntityManager entityManager) {
        String jpql = "select u from Usuario u";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setFirstResult(0)//qual registro comecar
                .setMaxResults(2);//quantidade de resultados por pagina
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    }

    public static void ordenandoResultados(EntityManager entityManager) {
        String jpql = "select u from Usuario u order by u.nome";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    }

    public static void utilizadoOperadorIn(EntityManager entityManager) {
        //traz os usuarios passados em um array
        String jpql = "select u from Usuario u where u.id in (:ids)";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setParameter("ids", Arrays.asList(1, 2));
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    }

    public static void utilizadoOperadoresLogicos(EntityManager entityManager) {
        String jpql = "select u from Usuario u where" +
                "(u.ultimoAcesso > :ontem and u.ultimoAcesso < :hoje) " +
                "or u.ultimoAcesso is null";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setParameter("ontem", LocalDateTime.now().minusDays(1))
                .setParameter("hoje", LocalDateTime.now());
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    }

    public static void filtrandoRegistros(EntityManager entityManager) {
        //LIKE, IS NULL, IS EMPTY, BETWEEN, >, <, >=, <=, =, <>
        //LIEK = select u from Usuario u where u.nome like concat(:nomeUsuario, '%')
        //IS NULL = select u from Uuario u where u.senha is null;
        //IS EMPTY = select d from Dominio d where d.usuarios is empty

        //String jpql = "select u from Usuario u where u.nome like :nomeUsuario";

        String jpql = "select u from Usuario u where u.ultimoAcesso between :ontem and :hoje";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
                .setParameter("ontem", LocalDateTime.now().minusDays(1))
                .setParameter("hoje", LocalDateTime.now());
                //.setParameter("nomeUsuario", "Cal%");
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    }

    public static void carregamentoComJoinFetch(EntityManager entityManager) {
        //traz a configuracao junto com o usuario fazendo apenas uma consulta
        String jpql = "select u from Usuario u join fetch u.configuracao join fetch u.dominio d";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    }

    public static void fazendoLeftJoing(EntityManager entityManager) {
        //so traz usuarios q possui cofiguracao
        //String jpql = "select u, c from Usuario u join u.configuracao c";

        //traz usuarios q possui ou nao cofiguracao
        String jpql = "select u, c from Usuario u left join u.configuracao c";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> {
            String out = ((Usuario) arr[0]).getNome();
            if(arr[1] == null) {
                out += ", NULL";
            } else {
                out += ", " + ((Configuracao) arr[1]).getId();
            }

            System.out.println(out);
        });
    }

    public static void fazendoJoins(EntityManager entityManager) {

        String jpql = "select u from Usuario u join u.dominio d where d.id = 1";
        //String sql = "select u.* from usuario u from join dominio d on u.dominio_id = d.id";

        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    }

    public static void passandoParamentros(EntityManager entityManager) {
        String jpql = "select u from Usuario u where u.id = :idUsuario";
        TypedQuery<Usuario> typedQuery = entityManager
                .createQuery(jpql, Usuario.class)
                .setParameter("idUsuario", 1);
        Usuario usuario = typedQuery.getSingleResult();
        System.out.println(usuario.getId() + ", " + usuario.getNome());

        String jpqlLog = "select u from Usuario u where u.login = :idUsuario";
        TypedQuery<Usuario> typedQueryLog = entityManager
                .createQuery(jpqlLog, Usuario.class)
                .setParameter("idUsuario", "ria");
        Usuario usuarioLog = typedQueryLog.getSingleResult();
        System.out.println(usuarioLog.getId() + ", " + usuarioLog.getNome());
    }

    public static void fazendoProjecoes(EntityManager entityManager) {
        String jpqlArr = "select id, login, nome from Usuario";
        TypedQuery<Object[]> typedQueryArr = entityManager.createQuery(jpqlArr, Object[].class);
        List<Object[]> listaArr = typedQueryArr.getResultList();
        listaArr.forEach(arr -> System.out.println(String.format("%s, %s, %s", arr)));


        String jpqlDto = "select new com.algaworks.sistemausuarios.dto.UsuarioDTO(id, login, nome)" +
                "from Usuario";
        TypedQuery<UsuarioDTO> typedQueryDto = entityManager.createQuery(jpqlDto, UsuarioDTO.class);
        List<UsuarioDTO> listaDto = typedQueryDto.getResultList();
        listaArr.forEach(arr -> System.out.println(String.format("%s, %s, %s", arr)));

        listaDto.forEach(u -> System.out.println("DTO: " + u.getId() + ", " + u.getNome()));
    }

    public static void escolhendoORetorno(EntityManager entityManager) {
        String jpql = "select u.dominio from Usuario u where u.id = 1";
        TypedQuery<Dominio> typedQuery = entityManager.createQuery(jpql, Dominio.class);
        Dominio dominio = typedQuery.getSingleResult();
        System.out.println(dominio.getId() + ", " + dominio.getNome());

        String jpqlNom = "select u.nome from Usuario u";
        TypedQuery<String> typedQueryNom = entityManager.createQuery(jpqlNom, String.class);
        List<String> listaNom = typedQueryNom.getResultList();
        listaNom.forEach(nome -> System.out.println(nome));
    }

    public static void primeiraConsulta(EntityManager entityManager) {
        String jpql = "select u from Usuario u";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));

        String jpqlSingle = "select u from Usuario u where u.id = 1";
        TypedQuery<Usuario> typedQuerySingle = entityManager.createQuery(jpqlSingle, Usuario.class);
        Usuario usuario = typedQuerySingle.getSingleResult();
        System.out.println(usuario.getId() + ", " + usuario.getNome());

        String jpqlCast = "select u from Usuario u where u.id = 2";
        Query query = entityManager.createQuery(jpqlCast);
        Usuario usuario2 = (Usuario) query.getSingleResult();
        System.out.println(usuario2.getId() + ", " + usuario2.getNome());
    }
}
