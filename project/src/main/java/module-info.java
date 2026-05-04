module drinkshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires org.controlsfx.controls;

    opens drinkshop.ui to javafx.fxml;
    exports drinkshop.ui;

    opens drinkshop.domain to javafx.base, org.mockito;
    exports drinkshop.domain;

    opens drinkshop.service to org.mockito;
    exports drinkshop.service;

    opens drinkshop.repository to org.mockito;
    exports drinkshop.repository;

    opens drinkshop.service.validator to org.mockito;
    exports drinkshop.service.validator;
}