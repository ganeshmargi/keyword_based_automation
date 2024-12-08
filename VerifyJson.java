import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.InvalidJsonException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonPathExample {

    public static void main(String[] args) {
        String jsonString = "{\n" +
                "  \"store\": {\n" +
                "    \"book\": [\n" +
                "      { \"category\": \"reference\", \"author\": \"Nigel Rees\", \"title\": \"Sayings of the Century\", \"price\": 8.95 },\n" +
                "      { \"category\": \"fiction\", \"author\": \"Evelyn Waugh\", \"title\": \"Sword of Honour\", \"price\": 12.99 }\n" +
                "    ],\n" +
                "    \"bicycle\": { \"color\": \"red\", \"price\": 19.95 }\n" +
                "  }\n" +
                "}";

        try {
            // Parse the JSON string
            Object document = JsonPath.parse(jsonString);

            // Example JSONPath expression to retrieve a specific value
            // Verify if the price of the first book is 8.95
            Double price = JsonPath.read(document, "$.store.book[0].price");
            System.out.println("Price of the first book: " + price);
            if (price == 8.95) {
                System.out.println("Price verification passed.");
            } else {
                System.out.println("Price verification failed.");
            }

            // Example JSONPath expression to get all book titles
            List<String> titles = JsonPath.read(document, "$.store.book[*].title");
            System.out.println("Titles of books: " + titles);
            
            // Verify if a certain title exists
            if (titles.contains("Sayings of the Century")) {
                System.out.println("Book title verification passed.");
            } else {
                System.out.println("Book title verification failed.");
            }

            // Check if a non-existing path returns an empty list or null
            List<String> nonExistingPath = JsonPath.read(document, "$.store.magazines");
            if (nonExistingPath.isEmpty()) {
                System.out.println("Non-existing path returned an empty list, as expected.");
            }

        } catch (InvalidJsonException e) {
            System.err.println("Invalid JSON: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
