package io.github.jhipster.cascade.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import io.github.jhipster.cascade.domain.enumeration.Language;

/**
 * A PoolInventory.
 */
@Entity
@Table(name = "pool_inventory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "poolinventory")
public class PoolInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "partnumber")
    private String partnumber;

    @Column(name = "partdescription")
    private String partdescription;

    @Column(name = "qtyin")
    private Integer qtyin;

    @Column(name = "qtyout")
    private Integer qtyout;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

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

    public PoolInventory date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPartnumber() {
        return partnumber;
    }

    public PoolInventory partnumber(String partnumber) {
        this.partnumber = partnumber;
        return this;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    public String getPartdescription() {
        return partdescription;
    }

    public PoolInventory partdescription(String partdescription) {
        this.partdescription = partdescription;
        return this;
    }

    public void setPartdescription(String partdescription) {
        this.partdescription = partdescription;
    }

    public Integer getQtyin() {
        return qtyin;
    }

    public PoolInventory qtyin(Integer qtyin) {
        this.qtyin = qtyin;
        return this;
    }

    public void setQtyin(Integer qtyin) {
        this.qtyin = qtyin;
    }

    public Integer getQtyout() {
        return qtyout;
    }

    public PoolInventory qtyout(Integer qtyout) {
        this.qtyout = qtyout;
        return this;
    }

    public void setQtyout(Integer qtyout) {
        this.qtyout = qtyout;
    }

    public Language getLanguage() {
        return language;
    }

    public PoolInventory language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PoolInventory)) {
            return false;
        }
        return id != null && id.equals(((PoolInventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PoolInventory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", partnumber='" + getPartnumber() + "'" +
            ", partdescription='" + getPartdescription() + "'" +
            ", qtyin=" + getQtyin() +
            ", qtyout=" + getQtyout() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
