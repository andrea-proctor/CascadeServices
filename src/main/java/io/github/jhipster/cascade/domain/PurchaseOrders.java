package io.github.jhipster.cascade.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A PurchaseOrders.
 */
@Entity
@Table(name = "purchase_orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "purchaseorders")
public class PurchaseOrders implements Serializable {

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
    @Column(name = "po_number", nullable = false)
    private String poNumber;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "part_description")
    private String partDescription;

    @Column(name = "qty_replaced")
    private Integer qtyReplaced;

    @Column(name = "replacement_cost_each")
    private String replacementCostEach;

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

    public PurchaseOrders site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public PurchaseOrders supervisor(String supervisor) {
        this.supervisor = supervisor;
        return this;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public PurchaseOrders poNumber(String poNumber) {
        this.poNumber = poNumber;
        return this;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public PurchaseOrders partNumber(String partNumber) {
        this.partNumber = partNumber;
        return this;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public PurchaseOrders partDescription(String partDescription) {
        this.partDescription = partDescription;
        return this;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Integer getQtyReplaced() {
        return qtyReplaced;
    }

    public PurchaseOrders qtyReplaced(Integer qtyReplaced) {
        this.qtyReplaced = qtyReplaced;
        return this;
    }

    public void setQtyReplaced(Integer qtyReplaced) {
        this.qtyReplaced = qtyReplaced;
    }

    public String getReplacementCostEach() {
        return replacementCostEach;
    }

    public PurchaseOrders replacementCostEach(String replacementCostEach) {
        this.replacementCostEach = replacementCostEach;
        return this;
    }

    public void setReplacementCostEach(String replacementCostEach) {
        this.replacementCostEach = replacementCostEach;
    }

    public String getFreightCharge() {
        return freightCharge;
    }

    public PurchaseOrders freightCharge(String freightCharge) {
        this.freightCharge = freightCharge;
        return this;
    }

    public void setFreightCharge(String freightCharge) {
        this.freightCharge = freightCharge;
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public PurchaseOrders totalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
        return this;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getNotes() {
        return notes;
    }

    public PurchaseOrders notes(String notes) {
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
        if (!(o instanceof PurchaseOrders)) {
            return false;
        }
        return id != null && id.equals(((PurchaseOrders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PurchaseOrders{" +
            "id=" + getId() +
            ", site='" + getSite() + "'" +
            ", supervisor='" + getSupervisor() + "'" +
            ", poNumber='" + getPoNumber() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", partDescription='" + getPartDescription() + "'" +
            ", qtyReplaced=" + getQtyReplaced() +
            ", replacementCostEach='" + getReplacementCostEach() + "'" +
            ", freightCharge='" + getFreightCharge() + "'" +
            ", totalCharge='" + getTotalCharge() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
