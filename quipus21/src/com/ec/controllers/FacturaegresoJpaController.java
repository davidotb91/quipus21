/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controllers;

import com.ec.controllers.exceptions.IllegalOrphanException;
import com.ec.controllers.exceptions.NonexistentEntityException;
import com.ec.controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.ec.entidades.Rubro;
import com.ec.entidades.Factura;
import com.ec.entidades.Facturaegreso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author david
 */
public class FacturaegresoJpaController implements Serializable {

    public FacturaegresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Facturaegreso facturaegreso) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Factura facturaOrphanCheck = facturaegreso.getFactura();
        if (facturaOrphanCheck != null) {
            Facturaegreso oldFacturaegresoOfFactura = facturaOrphanCheck.getFacturaegreso();
            if (oldFacturaegresoOfFactura != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Factura " + facturaOrphanCheck + " already has an item of type Facturaegreso whose factura column cannot be null. Please make another selection for the factura field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubro idrubroalcanzado = facturaegreso.getIdrubroalcanzado();
            if (idrubroalcanzado != null) {
                idrubroalcanzado = em.getReference(idrubroalcanzado.getClass(), idrubroalcanzado.getIdrubroalcanzado());
                facturaegreso.setIdrubroalcanzado(idrubroalcanzado);
            }
            Factura factura = facturaegreso.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getIdfactura());
                facturaegreso.setFactura(factura);
            }
            em.persist(facturaegreso);
            if (idrubroalcanzado != null) {
                idrubroalcanzado.getFacturaegresoCollection().add(facturaegreso);
                idrubroalcanzado = em.merge(idrubroalcanzado);
            }
            if (factura != null) {
                factura.setFacturaegreso(facturaegreso);
                factura = em.merge(factura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturaegreso(facturaegreso.getIdfactura()) != null) {
                throw new PreexistingEntityException("Facturaegreso " + facturaegreso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facturaegreso facturaegreso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturaegreso persistentFacturaegreso = em.find(Facturaegreso.class, facturaegreso.getIdfactura());
            Rubro idrubroalcanzadoOld = persistentFacturaegreso.getIdrubroalcanzado();
            Rubro idrubroalcanzadoNew = facturaegreso.getIdrubroalcanzado();
            Factura facturaOld = persistentFacturaegreso.getFactura();
            Factura facturaNew = facturaegreso.getFactura();
            List<String> illegalOrphanMessages = null;
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                Facturaegreso oldFacturaegresoOfFactura = facturaNew.getFacturaegreso();
                if (oldFacturaegresoOfFactura != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Factura " + facturaNew + " already has an item of type Facturaegreso whose factura column cannot be null. Please make another selection for the factura field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idrubroalcanzadoNew != null) {
                idrubroalcanzadoNew = em.getReference(idrubroalcanzadoNew.getClass(), idrubroalcanzadoNew.getIdrubroalcanzado());
                facturaegreso.setIdrubroalcanzado(idrubroalcanzadoNew);
            }
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getIdfactura());
                facturaegreso.setFactura(facturaNew);
            }
            facturaegreso = em.merge(facturaegreso);
            if (idrubroalcanzadoOld != null && !idrubroalcanzadoOld.equals(idrubroalcanzadoNew)) {
                idrubroalcanzadoOld.getFacturaegresoCollection().remove(facturaegreso);
                idrubroalcanzadoOld = em.merge(idrubroalcanzadoOld);
            }
            if (idrubroalcanzadoNew != null && !idrubroalcanzadoNew.equals(idrubroalcanzadoOld)) {
                idrubroalcanzadoNew.getFacturaegresoCollection().add(facturaegreso);
                idrubroalcanzadoNew = em.merge(idrubroalcanzadoNew);
            }
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.setFacturaegreso(null);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                facturaNew.setFacturaegreso(facturaegreso);
                facturaNew = em.merge(facturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaegreso.getIdfactura();
                if (findFacturaegreso(id) == null) {
                    throw new NonexistentEntityException("The facturaegreso with id " + id + " no longer exists.");
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
            Facturaegreso facturaegreso;
            try {
                facturaegreso = em.getReference(Facturaegreso.class, id);
                facturaegreso.getIdfactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaegreso with id " + id + " no longer exists.", enfe);
            }
            Rubro idrubroalcanzado = facturaegreso.getIdrubroalcanzado();
            if (idrubroalcanzado != null) {
                idrubroalcanzado.getFacturaegresoCollection().remove(facturaegreso);
                idrubroalcanzado = em.merge(idrubroalcanzado);
            }
            Factura factura = facturaegreso.getFactura();
            if (factura != null) {
                factura.setFacturaegreso(null);
                factura = em.merge(factura);
            }
            em.remove(facturaegreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facturaegreso> findFacturaegresoEntities() {
        return findFacturaegresoEntities(true, -1, -1);
    }

    public List<Facturaegreso> findFacturaegresoEntities(int maxResults, int firstResult) {
        return findFacturaegresoEntities(false, maxResults, firstResult);
    }

    private List<Facturaegreso> findFacturaegresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facturaegreso.class));
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

    public Facturaegreso findFacturaegreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facturaegreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaegresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facturaegreso> rt = cq.from(Facturaegreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
