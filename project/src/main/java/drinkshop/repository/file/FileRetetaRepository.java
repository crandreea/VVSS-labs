package drinkshop.repository.file;

import drinkshop.domain.IngredientReteta;
import drinkshop.domain.Reteta;
import drinkshop.domain.Ingredient;
import drinkshop.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileRetetaRepository
        extends FileAbstractRepository<Integer, Reteta> {

    private Repository<Long, Ingredient> ingredientRepo;

    public FileRetetaRepository(String fileName, Repository<Long, Ingredient> ingredientRepo) {
        super(fileName);
        this.ingredientRepo = ingredientRepo;
        loadFromFile();
    }

    @Override
    protected Integer getId(Reteta entity) {
        return entity.getId();
    }

    @Override
    protected Reteta extractEntity(String line) {

        String[] elems = line.split(",");

        int productId = Integer.parseInt(elems[0]);
        List<IngredientReteta> ingrediente = new ArrayList<>();
        int index=1;
        while (index<elems.length) {
            String ingredientTotal= elems[index++];
            String[] ingredientSeparat = ingredientTotal.split(":");
            Long ingredientId = Long.parseLong(ingredientSeparat[0]);
            Double ingredientQuantity = Double.parseDouble(ingredientSeparat[1]);
            
            Ingredient ing = ingredientRepo.findOne(ingredientId);
            
            ingrediente.add(new IngredientReteta(ing, ingredientQuantity));
        }
        return new Reteta(productId, ingrediente);
    }

    @Override
    protected String createEntityAsString(Reteta entity) {
        String ingrediente = entity.getIngrediente().stream()
                        .map(entry -> entry.getIngredient().getId() + ":" + entry.getCantitate())
                        .collect(Collectors.joining(","));
        return entity.getId() + "," +
                ingrediente;
    }
}
