package io.github.jhipster.cascade.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ServiceOrders.
 */
@Entity
@Table(name = "service_orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "serviceorders")
public class ServiceOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "site")
    private String site;

    @Column(name = "supervisor")
    private String supervisor;

    @NotNull
    @Column(name = "so_number", nullable = false)
    private String soNumber;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "part_description")
    private String partDescription;

    @Column(name = "repair_cost")
    private String repairCost;

    @Column(name = "freight_charge")
    private String freightCharge;

    @Column(name = "total_charge")
    private String totalCharge;

    @Column(name = "notes")
    private String notes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public ServiceOrders site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public ServiceOrders supervisor(String supervisor) {
        this.supervisor = supervisor;
        return this;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSoNumber() {
        return soNumber;
    }

    public ServiceOrders soNumber(String soNumber) {
        this.soNumber = soNumber;
        return this;
    }

    public void setSoNumber(String soNumber) {
        this.soNumber = soNumber;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public ServiceOrders partNumber(String partNumber) {
        this.partNumber = partNumber;
        return this;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public ServiceOrders partDescription(String partDescription) {
        this.partDescription = partDescription;
        return this;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getRepairCost() {
        return repairCost;
    }

    public ServiceOrders repairCost(String repairCost) {
        this.repairCost = repairCost;
        return this;
    }

    public void setRepairCost(String repairCost) {
        this.repairCost = repairCost;
    }

    public String getFreightCharge() {
        return freightCharge;
    }

    public ServiceOrders freightCharge(String freightCharge) {
        this.freightCharge = freightCharge;
        return this;
    }

    public void setFreightCharge(String freightCharge) {
        this.freightCharge = freightCharge;
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public ServiceOrders totalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
        return this;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getNotes() {
        return notes;
    }

    public ServiceOrders notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrders)) {
            return false;
        }
        return id != null && id.equals(((ServiceOrders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceOrders{" +
            "id=" + getId() +
            ", site='" + getSite() + "'" +
            ", supervisor='" + getSupervisor() + "'" +
            ", soNumber='" + getSoNumber() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", partDescription='" + getPartDescription() + "'" +
            ", repairCost='" + getRepairCost() + "'" +
            ", freightCharge='" + getFreightCharge() + "'" +
            ", totalCharge='" + getTotalCharge() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
