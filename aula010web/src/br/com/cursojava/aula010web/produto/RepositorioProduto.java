package br.com.cursojava.aula010web.produto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.cursojava.aula010web.utils.JPAUtil;

public class RepositorioProduto {
	
	public List<Produto> buscarTodos() {
		EntityManager em = JPAUtil.createEntityManager();
		TypedQuery<Produto> query = em.createQuery("select p from Produto p order by id", Produto.class);
		List<Produto> lista = query.getResultList();
		em.close();
		return lista;
	}
	
	public Produto buscarPorId(Integer id) {
		EntityManager em = JPAUtil.createEntityManager();
		Produto p = em.find(Produto.class, id);
		em.close();
		return p;
	}

	public List<Produto> buscarPorValor(double min, double max) {
		EntityManager em = JPAUtil.createEntityManager();
		TypedQuery<Produto> query = em.createQuery("select p from Produto p where (preco between :min and :max)", Produto.class);
		query.setParameter("min", min);
		query.setParameter("max", max);
		
		List<Produto> lista = query.getResultList();
		em.close();

		return lista;
	}
	
	public Produto salvar(Produto produto) {
		if (produto != null) {
			if (produto.getId() == null) {
				return inserir(produto);
			} else {
				return atualizar(produto);
			}
		}
		return produto;
	}

	private Produto atualizar(Produto produto) {
		EntityManager em = JPAUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			produto = em.merge(produto);
			em.getTransaction().commit();
		} catch (Exception e) {
			produto = null;
		}
		em.close();
		return produto;
	}

	private Produto inserir(Produto produto) {
		EntityManager em = JPAUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(produto);
			em.getTransaction().commit();
		} catch (Exception e) {
			produto = null;
		}
		em.close();
		return produto;
	}
	
	public Produto remover(Produto produto) {
		EntityManager em = JPAUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			produto = em.merge(produto);
			em.remove(produto);
			em.getTransaction().commit();
		} catch (Exception e) {
			produto = null;
		}
		em.close();
		return produto;
	}
}