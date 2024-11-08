package store.controller;

import store.view.OutputView;

public class ConvenientStoreController {

    private final DataInitializer dataInitializer;
    private final OutputView outputView;

    public ConvenientStoreController() {
        this.dataInitializer = new DataInitializer();
        this.outputView = new OutputView();
    }

    public void run() {
        dataInitializer.initialize();
        outputView.displayProducts();
    }
}
