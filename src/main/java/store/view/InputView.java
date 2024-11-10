package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.global.validator.InputValidator;

import static store.view.constants.InputMessage.*;

public class InputView {

    private static final String YES = "Y";

    public String readPurchaseProduct() {
        System.out.println(ASK_PRODUCT_NAME_AND_QUANTiTY);
        String input = Console.readLine();
        InputValidator.validateProductInput(input);
        return input;
    }

    public boolean readAdditionalPromotionProducts(String productName) {
        System.out.printf(ASK_ADDITIONAL_PROMOTION_PRODUCTS, productName);
        String input = Console.readLine();
        InputValidator.validateYesNoInput(input);
        return input.equals(YES);
    }

    public boolean readNonePromotionSalePurchase(String productName, int nonePromotionCount) {
        System.out.printf(ASK_NONE_PROMOTION_SALE_PURCHASE, productName, nonePromotionCount);
        String input = Console.readLine();
        InputValidator.validateYesNoInput(input);
        return input.equals(YES);
    }

    public boolean readMembershipSalePurchase() {
        System.out.println(ASK_MEMBERSHIP_SALE_PURCHASE);
        String input = Console.readLine();
        InputValidator.validateYesNoInput(input);
        return input.equals(YES);
    }

    public boolean readPurchaseContinue() {
        System.out.println(ASK_PURCHASE_CONTINUE);
        String input = Console.readLine();
        InputValidator.validateYesNoInput(input);
        return input.equals(YES);
    }
}
