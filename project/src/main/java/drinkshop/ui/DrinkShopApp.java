package drinkshop.ui;

import drinkshop.domain.*;
import drinkshop.repository.Repository;
import drinkshop.repository.file.FileCategorieRepository;
import drinkshop.repository.file.FileOrderRepository;
import drinkshop.repository.file.FileProductRepository;
import drinkshop.repository.file.FileRetetaRepository;
import drinkshop.repository.file.FileStocRepository;
import drinkshop.repository.file.FileTipBauturaRepository;
import drinkshop.service.DrinkShopService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrinkShopApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // ---------- Initializare Repository-uri Dictionar ----------
        Repository<Integer, CategorieBautura> catRepo = new FileCategorieRepository("data/categorii.txt");
        Repository<Integer, TipBautura> tipRepo = new FileTipBauturaRepository("data/tipuri.txt");
        Repository<Long, Ingredient> ingredientRepo = new drinkshop.repository.file.FileIngredientRepository("data/ingredients.txt");

        // ---------- Initializare Repository-uri Dependente ----------
        Repository<Integer, Product> productRepo = new FileProductRepository("data/products.txt", catRepo, tipRepo);
        Repository<Integer, Order> orderRepo = new FileOrderRepository("data/orders.txt", productRepo);
        Repository<Integer, Reteta> retetaRepo = new FileRetetaRepository("data/retete.txt", ingredientRepo);
        Repository<Integer, Stoc> stocRepo = new FileStocRepository("data/stocuri.txt", ingredientRepo);

        // ---------- Initializare Service ----------
        DrinkShopService service = new DrinkShopService(productRepo, orderRepo, retetaRepo, stocRepo, catRepo, tipRepo);

        // ---------- Incarcare FXML ----------

        FXMLLoader loader = new FXMLLoader(getClass().getResource("drinkshop.fxml"));
        Scene scene = new Scene(loader.load());

        // ---------- Setare Service in Controller ----------
        DrinkShopController controller = loader.getController();
        controller.setService(service);

        // ---------- Afisare Fereastra ----------
        stage.setTitle("Coffee Shop Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}