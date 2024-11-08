package store;

import store.controller.ConvenientStoreController;

public class Application {
    public static void main(String[] args) {
        ConvenientStoreController convenientStoreController = new ConvenientStoreController();
        convenientStoreController.run();
    }
}
