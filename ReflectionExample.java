import java.lang.reflect.Method;

public class ReflectionExample {
    public static void main(String[] args) {
        try {
            // Step 1: Obtain the Class object for Greeting
            Class<?> clazz = Class.forName("Greeting");

            // Step 2: Get the Method object
            Method method = clazz.getMethod("greet", String.class);

            // Step 3: Create an instance of Greeting
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // Step 4: Invoke the method
            method.invoke(instance, "World");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
