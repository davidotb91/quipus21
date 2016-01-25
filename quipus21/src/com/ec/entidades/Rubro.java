/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */
@Entity
@Table(name = "rubro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubro.findAll", query = "SELECT r FROM Rubro r"),
    @NamedQuery(name = "Rubro.findByIdrubroalcanzado", query = "SELECT r FROM Rubro r WHERE r.idrubroalcanzado = :idrubroalcanzado"),
    @NamedQuery(name = "Rubro.findByNombrerubro", query = "SELECT r FROM Rubro r WHERE r.nombrerubro = :nombrerubro"),
    @NamedQuery(name = "Rubro.findByValortotalrubro", query = "SELECT r FROM Rubro r WHERE r.valortotalrubro = :valortotalrubro"),
    @NamedQuery(name = "Rubro.findByCodigorubro", query = "SELECT r FROM Rubro r WHERE r.codigorubro = :codigorubro")})
public class Rubro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDRUBROALCANZADO")
    private Integer idrubroalcanzado;
    @Column(name = "NOMBRERUBRO")
    private String nombrerubro;
    @Column(name = "VALORTOTALRUBRO")
    private String valortotalrubro;
    @Column(name = "CODIGORUBRO")
    private String codigorubro;
    @OneToMany(mappedBy = "idrubroalcanzado")
    private Collection<Facturaegreso> facturaegresoCollection;

    public Rubro() {
    }

    public Rubro(Integer idrubroalcanzado) {
        this.idrubroalcanzado = idrubroalcanzado;
    }

    public Integer getIdrubroalcanzado() {
        return idrubroalcanzado;
    }

    public void setIdrubroalcanzado(Integer idrubroalcanzado) {
        this.idrubroalcanzado = idrubroalcanzado;
    }

    public String getNombrerubro() {
        return nombrerubro;
    }

    public void setNombrerubro(String nombrerubro) {
        this.nombrerubro = nombrerubro;
    }

    public String getValortotalrubro() {
        return valortotalrubro;
    }

    public void setValortotalrubro(String valortotalrubro) {
        this.valortotalrubro = valortotalrubro;
    }

    public String getCodigorubro() {
        return codigorubro;
    }

    public void setCodigorubro(String codigorubro) {
        this.codigorubro = codigorubro;
    }

    @XmlTransient
    public Collection<Facturaegreso> getFacturaegresoCollection() {
        return facturaegresoCollection;
    }

    public void setFacturaegresoCollection(Collection<Facturaegreso> facturaegresoCollection) {
        this.facturaegresoCollection = facturaegresoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrubroalcanzado != null ? idrubroalcanzado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubro)) {
            return false;
        }
        Rubro other = (Rubro) object;
        if ((this.idrubroalcanzado == null && other.idrubroalcanzado != null) || (this.idrubroalcanzado != null && !this.idrubroalcanzado.equals(other.idrubroalcanzado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidades.Rubro[ idrubroalcanzado=" + idrubroalcanzado + " ]";
    }
    
}
