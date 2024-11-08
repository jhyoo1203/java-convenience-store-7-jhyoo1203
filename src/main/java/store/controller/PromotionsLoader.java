package store.controller;

import store.domain.events.Promotion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PromotionsLoader {

    private static final Path RESOURCE_PATH = Path.of("src", "main", "resources");
    private static final String PROMOTION_FILENAME = "promotions.md";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int HEADER_INDEX = 1;
    private static final String DELIMITER = ",";

    public List<Promotion> loadPromotions() {
        Path promotionsPath = RESOURCE_PATH.resolve(PROMOTION_FILENAME);

        try (Stream<String> lines = Files.lines(promotionsPath)) {
            return parsePromotions(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Promotion> parsePromotions(Stream<String> lines) {
        return lines.skip(HEADER_INDEX)
                .map(line -> Arrays.asList(line.split(DELIMITER)))
                .map(this::createPromotion)
                .toList();
    }

    private Promotion createPromotion(List<String> values) {
        return Promotion.from(
                values.getFirst(),
                Integer.parseInt(values.get(1)),
                Integer.parseInt(values.get(2)),
                parseDate(values.get(3)),
                parseDate(values.get(4))
        );
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }
}
