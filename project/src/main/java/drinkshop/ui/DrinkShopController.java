package drinkshop.ui;

import drinkshop.domain.*;
import drinkshop.service.DrinkShopService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DrinkShopController {

    private DrinkShopService service;

    // ---------- PRODUCT ----------
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> colProdId;
    @FXML private TableColumn<Product, String> colProdName;
    @FXML private TableColumn<Product, Double> colProdPrice;
    @FXML private TableColumn<Product, CategorieBautura> colProdCategorie;
    @FXML private TableColumn<Product, TipBautura> colProdTip;
    @FXML private TextField txtProdName, txtProdPrice;
    @FXML private ComboBox<CategorieBautura> comboProdCategorie;
    @FXML private ComboBox<TipBautura> comboProdTip;

    // ---------- RETETE ----------
    @FXML private TableView<Reteta> retetaTable;
    @FXML private TableColumn<Reteta, Integer> colRetetaId;
    @FXML private TableColumn<Reteta, String> colRetetaDesc;

    @FXML private TableView<IngredientReteta> newRetetaTable;
    @FXML private TableColumn<IngredientReteta, String> colNewIngredName;
    @FXML private TableColumn<IngredientReteta, Double> colNewIngredCant;
    @FXML private TextField txtNewIngredName, txtNewIngredCant;

    // ---------- ORDER (CURRENT) ----------
    @FXML private TableView<OrderItem> currentOrderTable;
    @FXML private TableColumn<OrderItem, String> colOrderProdName;
    @FXML private TableColumn<OrderItem, Integer> colOrderQty;

    @FXML private ComboBox<Integer> comboQty;
    @FXML private Label lblOrderTotal;
    @FXML private TextArea txtReceipt;

    @FXML private Label lblTotalRevenue;

    // ---------- CATEGORII & TIPURI ----------
    @FXML private TableView<CategorieBautura> categorieTable;
    @FXML private TableColumn<CategorieBautura, Integer> colCategorieId;
    @FXML private TableColumn<CategorieBautura, String> colCategorieName;
    @FXML private TextField txtCategorieName;

    @FXML private TableView<TipBautura> tipTable;
    @FXML private TableColumn<TipBautura, Integer> colTipId;
    @FXML private TableColumn<TipBautura, String> colTipName;
    @FXML private TextField txtTipName;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private ObservableList<Reteta> retetaList = FXCollections.observableArrayList();
    private ObservableList<IngredientReteta> newRetetaList = FXCollections.observableArrayList();
    private ObservableList<OrderItem> currentOrderItems = FXCollections.observableArrayList();
    private ObservableList<CategorieBautura> categorieList = FXCollections.observableArrayList();
    private ObservableList<TipBautura> tipList = FXCollections.observableArrayList();

    private Order currentOrder = new Order(1);

    public void setService(DrinkShopService service) {
        this.service = service;
        initData();
    }

    @FXML
    private void initialize() {

        // PRODUCTS
        colProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdName.setCellValueFactory(new PropertyValueFactory<>("nume"));
        colProdPrice.setCellValueFactory(new PropertyValueFactory<>("pret"));
        colProdCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colProdTip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        productTable.setItems(productList);

        // RETETE
        colRetetaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRetetaDesc.setCellValueFactory(data -> {
            Reteta r = data.getValue();
            String desc = r.getIngrediente().stream()
                    .map(i -> i.getIngredient().getNume() + " (" + i.getCantitate() + ")")
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(desc);
        });
        retetaTable.setItems(retetaList);

        colNewIngredName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIngredient().getNume()));
        colNewIngredCant.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        newRetetaTable.setItems(newRetetaList);

        // CURRENT ORDER TABLE
        colOrderProdName.setCellValueFactory(data -> {
            int prodId = data.getValue().getProduct().getId();
            Product p = productList.stream().filter(pr -> pr.getId() == prodId).findFirst().orElse(null);
            return new SimpleStringProperty(p != null ? p.getNume() : "N/A");
        });
        colOrderQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        currentOrderTable.setItems(currentOrderItems);

        comboQty.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10));

        // CATEGORII
        colCategorieId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategorieName.setCellValueFactory(new PropertyValueFactory<>("nume"));
        categorieTable.setItems(categorieList);

        // TIPURI
        colTipId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipName.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tipTable.setItems(tipList);
    }

    private void initData() {
        productList.setAll(service.getAllProducts());
        retetaList.setAll(service.getAllRetete());
        categorieList.setAll(service.getAllCategorii());
        tipList.setAll(service.getAllTipuri());
        comboProdCategorie.getItems().setAll(categorieList);
        comboProdTip.getItems().setAll(tipList);
        lblTotalRevenue.setText("Daily Revenue: " + service.getDailyRevenue());
        updateOrderTotal();
    }

    // ---------- PRODUCT ----------
    @FXML
    private void onAddProduct() {
        Reteta r=retetaTable.getSelectionModel().getSelectedItem();

        if (r == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Selectati o reteta pentru care adugati un produs");
            alert.showAndWait();
            return;
        }else
        if (service.getAllProducts().stream().filter(p->p.getId()==r.getId()).toList().size()>0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Exista un produs cu reteta adaugata.");
            alert.showAndWait();
            return;
        }
        Product p = new Product(r.getId(),
                txtProdName.getText(),
                Double.parseDouble(txtProdPrice.getText()),
                comboProdCategorie.getValue(),
                comboProdTip.getValue());
        service.addProduct(p);
        initData();
    }

    @FXML
    private void onUpdateProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        service.updateProduct(selected.getId(), txtProdName.getText(),
                Double.parseDouble(txtProdPrice.getText()),
                comboProdCategorie.getValue(), comboProdTip.getValue());
        initData();
    }

    @FXML
    private void onDeleteProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        service.deleteProduct(selected.getId());
        initData();
    }

    @FXML
    private void onFilterCategorie() {
        productList.setAll(service.filtreazaDupaCategorie(comboProdCategorie.getValue()));
    }

    @FXML
    private void onFilterTip() {
        productList.setAll(service.filtreazaDupaTip(comboProdTip.getValue()));
    }

    // ---------- RETETA NOUA ----------
    @FXML
    private void onAddNewIngred() {
        newRetetaList.add(new IngredientReteta(new drinkshop.domain.Ingredient((long) (Math.random() * 10000), txtNewIngredName.getText(), "buc"),
                Double.parseDouble(txtNewIngredCant.getText())));
    }

    @FXML
    private void onDeleteNewIngred() {
        IngredientReteta sel = newRetetaTable.getSelectionModel().getSelectedItem();
        if (sel != null) newRetetaList.remove(sel);
    }

    @FXML
    private void onAddNewReteta() {
        Reteta r = new Reteta(service.getAllRetete().size()+1, new ArrayList<>(newRetetaList));
        service.addReteta(r);
        newRetetaList.clear();
        initData();
    }

    @FXML
    private void onClearNewRetetaIngredients() {
        newRetetaTable.getItems().clear();
        txtNewIngredName.clear();
        txtNewIngredCant.clear();
    }

    // ---------- CURRENT ORDER ----------
    @FXML
    private void onAddOrderItem() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        Integer qty = comboQty.getValue();

        if (selected == null) {
            showError("Selectează un produs din listă.");
            return;
        }
        if (qty == null) {
            showError("Selectează cantitatea.");
            return;
        }

        currentOrderItems.add(new OrderItem(selected, qty));
        updateOrderTotal();
    }

    @FXML
    private void onDeleteOrderItem() {
        OrderItem sel = currentOrderTable.getSelectionModel().getSelectedItem();
        if (sel != null) {
            currentOrderItems.remove(sel);
            updateOrderTotal();
        }
    }

    @FXML
    private void onFinalizeOrder() {
        currentOrder.getItems().clear();
        currentOrder.getItems().addAll(currentOrderItems);
        currentOrder.computeTotalPrice();

        service.addOrder(currentOrder);
        txtReceipt.setText(service.generateReceipt(currentOrder));

        currentOrderItems.clear();
        currentOrder = new Order(currentOrder.getId() + 1);
        updateOrderTotal();
    }

    private void updateOrderTotal() {
        currentOrder.getItems().clear();
        currentOrder.getItems().addAll(currentOrderItems);
        double total = service.computeTotal(currentOrder);
        lblOrderTotal.setText("Total: " + total);
    }

    // ---------- EXPORT + REVENUE ----------
    @FXML
    private void onExportOrdersCsv() {
        service.exportCsv("orders.csv");
    }

    @FXML
    private void onDailyRevenue() {
        lblTotalRevenue.setText("Daily Revenue: " + service.getDailyRevenue());
    }

    // ---------- CATEGORII ----------
    @FXML
    private void onAddCategorie() {
        if (txtCategorieName.getText().isEmpty()) return;
        int nextId = categorieList.stream().mapToInt(CategorieBautura::getId).max().orElse(0) + 1;
        CategorieBautura cat = new CategorieBautura(nextId, txtCategorieName.getText());
        service.addCategorie(cat);
        initData();
        txtCategorieName.clear();
    }

    @FXML
    private void onUpdateCategorie() {
        CategorieBautura sel = categorieTable.getSelectionModel().getSelectedItem();
        if (sel == null || txtCategorieName.getText().isEmpty()) return;
        sel.setNume(txtCategorieName.getText());
        service.updateCategorie(sel);
        initData();
        txtCategorieName.clear();
    }

    @FXML
    private void onDeleteCategorie() {
        CategorieBautura sel = categorieTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        service.deleteCategorie(sel.getId());
        initData();
    }

    // ---------- TIPURI BAUTURA ----------
    @FXML
    private void onAddTip() {
        if (txtTipName.getText().isEmpty()) return;
        int nextId = tipList.stream().mapToInt(TipBautura::getId).max().orElse(0) + 1;
        TipBautura tip = new TipBautura(nextId, txtTipName.getText());
        service.addTip(tip);
        initData();
        txtTipName.clear();
    }

    @FXML
    private void onUpdateTip() {
        TipBautura sel = tipTable.getSelectionModel().getSelectedItem();
        if (sel == null || txtTipName.getText().isEmpty()) return;
        sel.setNume(txtTipName.getText());
        service.updateTip(sel);
        initData();
        txtTipName.clear();
    }

    @FXML
    private void onDeleteTip() {
        TipBautura sel = tipTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        service.deleteTip(sel.getId());
        initData();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}