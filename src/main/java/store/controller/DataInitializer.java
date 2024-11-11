package store.controller;

import store.domain.events.Promotions;
import store.domain.products.Products;

public class DataInitializer {

    private final PromotionsLoader promotionsLoader;
    private final ProductsLoader productsLoader;

    public DataInitializer() {
        this.promotionsLoader = new PromotionsLoader();
        this.productsLoader = new ProductsLoader();
    }

    public void initialize() {
        promotionsLoader.loadPromotions().forEach(Promotions::addPromotion);
        productsLoader.loadProducts().forEach(Products::addProduct);
    }
}
