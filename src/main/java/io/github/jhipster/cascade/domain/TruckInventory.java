package io.github.jhipster.cascade.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TruckInventory.
 */
@Entity
@Table(name = "truck_inventory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "truckinventory")
public class TruckInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "date")
    private String date;

    @NotNull
    @Column(name = "truck_number", nullable = false)
    private String truckNumber;

    @Column(name = "site")
    private String site;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "part_description")
    private String partDescription;

    @Column(name = "category")
    private String category;

    @Column(name = "qty_on_hand")
    private Integer qtyOnHand;

    @Column(name = "qty_out")
    private Integer qtyOut;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public TruckInventory date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public TruckInventory truckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
        return this;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getSite() {
        return site;
    }

    public TruckInventory site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public TruckInventory partNumber(String partNumber) {
        this.partNumber = partNumber;
        return this;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public TruckInventory partDescription(String partDescription) {
        this.partDescription = partDescription;
        return this;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getCategory() {
        return category;
    }

    public TruckInventory category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQtyOnHand() {
        return qtyOnHand;
    }

    public TruckInventory qtyOnHand(Integer qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
        return this;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public Integer getQtyOut() {
        return qtyOut;
    }

    public TruckInventory qtyOut(Integer qtyOut) {
        this.qtyOut = qtyOut;
        return this;
    }

    public void setQtyOut(Integer qtyOut) {
        this.qtyOut = qtyOut;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TruckInventory)) {
            return false;
        }
        return id != null && id.equals(((TruckInventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TruckInventory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", truckNumber='" + getTruckNumber() + "'" +
            ", site='" + getSite() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", partDescription='" + getPartDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", qtyOnHand=" + getQtyOnHand() +
            ", qtyOut=" + getQtyOut() +
            "}";
    }
}
