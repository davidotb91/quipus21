/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controllers;

import com.ec.controllers.exceptions.NonexistentEntityException;
import com.ec.controllers.exceptions.PreexistingEntityException;
import com.ec.entidades.Rubroreferencia;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author david
 */
public class RubroreferenciaJpaController implements Serializable {

    public RubroreferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rubroreferencia rubroreferencia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rubroreferencia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRubroreferencia(rubroreferencia.getIdRubromodelo()) != null) {
                throw new PreexistingEntityException("Rubroreferencia " + rubroreferencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rubroreferencia rubroreferencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rubroreferencia = em.merge(rubroreferencia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rubroreferencia.getIdRubromodelo();
                if (findRubroreferencia(id) == null) {
                    throw new NonexistentEntityException("The rubroreferencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubroreferencia rubroreferencia;
            try {
                rubroreferencia = em.getReference(Rubroreferencia.class, id);
                rubroreferencia.getIdRubromodelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rubroreferencia with id " + id + " no longer exists.", enfe);
            }
            em.remove(rubroreferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rubroreferencia> findRubroreferenciaEntities() {
        return findRubroreferenciaEntities(true, -1, -1);
    }

    public List<Rubroreferencia> findRubroreferenciaEntities(int maxResults, int firstResult) {
        return findRubroreferenciaEntities(false, maxResults, firstResult);
    }

    private List<Rubroreferencia> findRubroreferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rubroreferencia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rubroreferencia findRubroreferencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rubroreferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getRubroreferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rubroreferencia> rt = cq.from(Rubroreferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
