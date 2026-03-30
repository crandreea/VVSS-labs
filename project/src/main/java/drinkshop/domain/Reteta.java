package drinkshop.domain;

import java.util.List;

public class Reteta {

    private int productId;
    private List<IngredientReteta> ingrediente;

    public Reteta(int productId, List<IngredientReteta> ingrediente) {
        this.productId = productId;
        this.ingrediente = ingrediente;

    }

    public void setId(int id) {
        this.productId = id;
    }

    public int getId() {
        return productId;
    }


    public List<IngredientReteta> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(List<IngredientReteta> ingrediente) {
        this.ingrediente = ingrediente;
    }

    @Override
    public String toString() {
        return "Reteta{" +
                "productId=" + productId +
                ", ingrediente=" + ingrediente +
                '}';
    }
}

