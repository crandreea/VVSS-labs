package drinkshop.domain;

import java.util.Objects;

public class Ingredient {
    private Long id;
    private String nume;
    private String unitateDeMasura;

    public Ingredient(Long id, String nume, String unitateDeMasura) {
        this.id = id;
        this.nume = nume;
        this.unitateDeMasura = unitateDeMasura;
    }

    public Ingredient(String nume) {
        this(null, nume, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUnitateDeMasura() {
        return unitateDeMasura;
    }

    public void setUnitateDeMasura(String unitateDeMasura) {
        this.unitateDeMasura = unitateDeMasura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(nume, that.nume); // Still comparing mostly by nume as before? Let's use ID and nume.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume);
    }

    @Override
    public String toString() {
        return nume + (unitateDeMasura != null && !unitateDeMasura.isEmpty() ? " [" + unitateDeMasura + "]" : "");
    }
}
