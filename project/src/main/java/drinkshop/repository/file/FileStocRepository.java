package drinkshop.repository.file;

import drinkshop.domain.Stoc;
import drinkshop.domain.Ingredient;
import drinkshop.repository.Repository;

public class FileStocRepository
        extends FileAbstractRepository<Integer, Stoc> {

    private Repository<Long, Ingredient> ingredientRepo;

    public FileStocRepository(String fileName, Repository<Long, Ingredient> ingredientRepo) {
        super(fileName);
        this.ingredientRepo = ingredientRepo;
        loadFromFile();
    }

    @Override
    protected Integer getId(Stoc entity) {
        return entity.getId();
    }

    @Override
    protected Stoc extractEntity(String line) {
        String[] elems = line.split(";");

        int id = Integer.parseInt(elems[0]);
        Long ingredientId = Long.parseLong(elems[1]);
        int cantitate = Integer.parseInt(elems[2]);
        int stocMinim = Integer.parseInt(elems[3]);

        Ingredient ing = ingredientRepo.findOne(ingredientId);

        return new Stoc(id, ing, cantitate, stocMinim);
    }

    @Override
    protected String createEntityAsString(Stoc entity) {
        return entity.getId() + ";" +
                entity.getIngredient().getId() + ";" +
                entity.getCantitate() + ";" +
                entity.getStocMinim();
    }
}