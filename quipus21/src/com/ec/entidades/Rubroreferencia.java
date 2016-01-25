/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david
 */
@Entity
@Table(name = "rubroreferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubroreferencia.findAll", query = "SELECT r FROM Rubroreferencia r"),
    @NamedQuery(name = "Rubroreferencia.findByIdRubromodelo", query = "SELECT r FROM Rubroreferencia r WHERE r.idRubromodelo = :idRubromodelo"),
    @NamedQuery(name = "Rubroreferencia.findByNombrerubro", query = "SELECT r FROM Rubroreferencia r WHERE r.nombrerubro = :nombrerubro"),
    @NamedQuery(name = "Rubroreferencia.findByValormaximo", query = "SELECT r FROM Rubroreferencia r WHERE r.valormaximo = :valormaximo"),
    @NamedQuery(name = "Rubroreferencia.findByCodigorubro", query = "SELECT r FROM Rubroreferencia r WHERE r.codigorubro = :codigorubro")})
public class Rubroreferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RUBROMODELO")
    private Integer idRubromodelo;
    @Column(name = "NOMBRERUBRO")
    private String nombrerubro;
    @Column(name = "VALORMAXIMO")
    private String valormaximo;
    @Basic(optional = false)
    @Column(name = "CODIGORUBRO")
    private String codigorubro;

    public Rubroreferencia() {
    }

    public Rubroreferencia(Integer idRubromodelo) {
        this.idRubromodelo = idRubromodelo;
    }

    public Rubroreferencia(Integer idRubromodelo, String codigorubro) {
        this.idRubromodelo = idRubromodelo;
        this.codigorubro = codigorubro;
    }

    public Integer getIdRubromodelo() {
        return idRubromodelo;
    }

    public void setIdRubromodelo(Integer idRubromodelo) {
        this.idRubromodelo = idRubromodelo;
    }

    public String getNombrerubro() {
        return nombrerubro;
    }

    public void setNombrerubro(String nombrerubro) {
        this.nombrerubro = nombrerubro;
    }

    public String getValormaximo() {
        return valormaximo;
    }

    public void setValormaximo(String valormaximo) {
        this.valormaximo = valormaximo;
    }

    public String getCodigorubro() {
        return codigorubro;
    }

    public void setCodigorubro(String codigorubro) {
        this.codigorubro = codigorubro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRubromodelo != null ? idRubromodelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubroreferencia)) {
            return false;
        }
        Rubroreferencia other = (Rubroreferencia) object;
        if ((this.idRubromodelo == null && other.idRubromodelo != null) || (this.idRubromodelo != null && !this.idRubromodelo.equals(other.idRubromodelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidades.Rubroreferencia[ idRubromodelo=" + idRubromodelo + " ]";
    }
    
}
