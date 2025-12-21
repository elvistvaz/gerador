import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String[] passwords = {"admin123", "123456", "admin", "password"};
        String storedHash = "$2a$10$N.vDq0xMBkxWCy7J4qZ8X.QZzCG9mNUYI1JNRxB/uSGq2eKGvN7uW";

        System.out.println("Testing passwords against stored hash:");
        for (String password : passwords) {
            boolean matches = encoder.matches(password, storedHash);
            System.out.println("  " + password + " -> " + matches);
        }

        System.out.println("\nGenerating new hashes:");
        for (String password : passwords) {
            String hash = encoder.encode(password);
            System.out.println("  " + password + " -> " + hash);
        }
    }
}
