package drinkshop.service;

import java.util.List;

import drinkshop.domain.IngredientReteta;
import drinkshop.domain.Reteta;
import drinkshop.domain.Stoc;
import drinkshop.repository.Repository;

public class StocService {

    private final Repository<Integer, Stoc> stocRepo;

    public StocService(Repository<Integer, Stoc> stocRepo) {
        this.stocRepo = stocRepo;
    }

    public List<Stoc> getAll() {
        return stocRepo.findAll();
    }

    public void add(Stoc s) {
        stocRepo.save(s);
    }

    public void update(Stoc s) {
        stocRepo.update(s);
    }

    public void delete(int id) {
        stocRepo.delete(id);
    }

    public boolean areSuficient(Reteta reteta) {
        List<IngredientReteta> ingredienteNecesare = reteta.getIngrediente();

        for (IngredientReteta e : ingredienteNecesare) {
            String ingredient = e.getIngredient().getNume();
            double necesar = e.getCantitate();

            double disponibil = stocRepo.findAll().stream()
                    .filter(s -> s.getIngredient().getNume().equalsIgnoreCase(ingredient))
                    .mapToDouble(Stoc::getCantitate)
                    .sum();

            if (disponibil < necesar) {
                return false;
            }
        }
        return true;
    }

    public void consuma(Reteta reteta) {
        if (!areSuficient(reteta)) {
            throw new IllegalStateException("Stoc insuficient pentru rețeta.");
        }

        for (IngredientReteta e : reteta.getIngrediente()) {
            String ingredient = e.getIngredient().getNume();
            double necesar = e.getCantitate();

            List<Stoc> ingredienteStoc = stocRepo.findAll().stream()
                    .filter(s -> s.getIngredient().getNume().equalsIgnoreCase(ingredient))
                    .toList();

            double ramas = necesar;

            for (Stoc s : ingredienteStoc) {
                if (ramas <= 0) break;

                double deScazut = Math.min(s.getCantitate(), ramas);
                s.setCantitate((s.getCantitate() - deScazut));
                ramas -= deScazut;

                stocRepo.update(s);
            }
        }
    }
}