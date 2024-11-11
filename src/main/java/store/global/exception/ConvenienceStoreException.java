package store.global.exception;

public class ConvenienceStoreException extends IllegalArgumentException {

    private ConvenienceStoreException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    private ConvenienceStoreException(
            ErrorMessage errorMessage,
            Exception exception
    ) {
        super(errorMessage.getMessage(), exception);
    }

    public static ConvenienceStoreException from(ErrorMessage errorMessage) {
        return new ConvenienceStoreException(errorMessage);
    }

    public static ConvenienceStoreException of(
            ErrorMessage errorMessage,
            Exception exception
    ) {
        return new ConvenienceStoreException(errorMessage, exception);
    }
}
