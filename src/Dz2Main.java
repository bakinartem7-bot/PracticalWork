import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Dz2Main {

    public static void main(String[] args) throws IOException {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(openStudentsFile(), StandardCharsets.UTF_8))) {

            String name = null;
            List<Book> books = null;

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String trimmed = line.trim();

                if (trimmed.isEmpty()) {
                    if (name != null) {
                        students.add(new Student(name, books));
                        name = null;
                        books = null;
                    }
                } else if (trimmed.startsWith("Student:")) {
                    if (name != null) {
                        students.add(new Student(name, books));
                    }
                    name = trimmed.substring("Student:".length()).trim();
                    books = new ArrayList<>();
                } else if (trimmed.startsWith("Book:") && books != null) {
                    String[] parts = trimmed.substring("Book:".length()).trim().split("\\|");
                    books.add(new Book(
                            parts[0].trim(),
                            parts[1].trim(),
                            Integer.parseInt(parts[2].trim()),
                            Integer.parseInt(parts[3].trim())));
                }
            }

            if (name != null) {
                students.add(new Student(name, books));
            }
        }

        students.stream()
                .peek(System.out::println)
                .flatMap(student -> student.getBooks().stream())
                .sorted(Comparator.comparingInt(Book::getPages))
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .limit(3)
                .map(Book::getYear)
                .reduce(Optional.<Integer>empty(),
                        (found, year) -> found.or(() -> Optional.of(year)),
                        (first, second) -> first.or(() -> second))
                .ifPresentOrElse(
                        year -> System.out.println("Год выпуска найденной книги: " + year),
                        () -> System.out.println("Такая книга отсутствует"));
    }

    private static InputStream openStudentsFile() throws IOException {
        InputStream fromClasspath = Dz2Main.class.getResourceAsStream("/students.txt");
        if (fromClasspath != null) {
            return fromClasspath;
        }
        return Files.newInputStream(Paths.get("resources", "students.txt"));
    }
}
