package work.vietdefi.spring.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convert a Java object to a JSON string
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // You might want to handle this differently in a real application
        }
    }
    // Convert JSON string to Java object
    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // Handle the error as needed
        }
    }
}
