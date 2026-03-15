package drinkshop.repository.file;

import drinkshop.domain.CategorieBautura;

public class FileCategorieRepository extends FileAbstractRepository<Integer, CategorieBautura> {

    public FileCategorieRepository(String fileName) {
        super(fileName);
        loadFromFile();
    }

    @Override
    protected Integer getId(CategorieBautura entity) {
        return entity.getId();
    }

    @Override
    protected CategorieBautura extractEntity(String line) {
        String[] elems = line.split(";");
        int id = Integer.parseInt(elems[0]);
        String nume = elems[1];
        return new CategorieBautura(id, nume);
    }

    @Override
    protected String createEntityAsString(CategorieBautura entity) {
        return entity.getId() + ";" + entity.getNume();
    }
}
