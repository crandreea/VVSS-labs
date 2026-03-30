package drinkshop.repository.file;

import drinkshop.domain.Product;
import drinkshop.domain.CategorieBautura;
import drinkshop.domain.TipBautura;
import drinkshop.repository.Repository;

public class FileProductRepository
        extends FileAbstractRepository<Integer, Product> {

    private Repository<Integer, CategorieBautura> categorieRepo;
    private Repository<Integer, TipBautura> tipRepo;

    public FileProductRepository(String fileName, Repository<Integer, CategorieBautura> categorieRepo, Repository<Integer, TipBautura> tipRepo) {
        super(fileName);
        this.categorieRepo = categorieRepo;
        this.tipRepo = tipRepo;
        loadFromFile();
    }

    @Override
    protected Integer getId(Product entity) {
        return entity.getId();
    }

    @Override
    protected Product extractEntity(String line) {

        String[] elems = line.split(",");

        int id = Integer.parseInt(elems[0]);
        String name = elems[1];
        double price = Double.parseDouble(elems[2]);
        CategorieBautura categorie = categorieRepo.findOne(Integer.parseInt(elems[3]));
        TipBautura tip = tipRepo.findOne(Integer.parseInt(elems[4]));

        return new Product(id, name, price, categorie, tip);
    }

    @Override
    protected String createEntityAsString(Product entity) {
        return entity.getId() + "," +
                entity.getNume() + "," +
                entity.getPret() + "," +
                entity.getCategorie().getId() + "," +
                entity.getTip().getId();
    }
}