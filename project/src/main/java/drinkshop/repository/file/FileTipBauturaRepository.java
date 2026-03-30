package drinkshop.repository.file;

import drinkshop.domain.TipBautura;

public class FileTipBauturaRepository extends FileAbstractRepository<Integer, TipBautura> {

    public FileTipBauturaRepository(String fileName) {
        super(fileName);
        loadFromFile();
    }

    @Override
    protected Integer getId(TipBautura entity) {
        return entity.getId();
    }

    @Override
    protected TipBautura extractEntity(String line) {
        String[] elems = line.split(";");
        int id = Integer.parseInt(elems[0]);
        String nume = elems[1];
        return new TipBautura(id, nume);
    }

    @Override
    protected String createEntityAsString(TipBautura entity) {
        return entity.getId() + ";" + entity.getNume();
    }
}
