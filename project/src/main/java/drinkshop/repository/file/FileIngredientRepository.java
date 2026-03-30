package drinkshop.repository.file;

import drinkshop.domain.Ingredient;

public class FileIngredientRepository extends FileAbstractRepository<Long, Ingredient> {

    public FileIngredientRepository(String fileName) {
        super(fileName);
        loadFromFile();
    }

    @Override
    protected Long getId(Ingredient entity) {
        return entity.getId();
    }

    @Override
    protected Ingredient extractEntity(String line) {
        String[] elems = line.split(";");
        Long id = Long.parseLong(elems[0]);
        String nume = elems[1];
        String unitateDeMasura = elems[2];
        return new Ingredient(id, nume, unitateDeMasura);
    }

    @Override
    protected String createEntityAsString(Ingredient entity) {
        return entity.getId() + ";" + entity.getNume() + ";" + entity.getUnitateDeMasura();
    }
}
