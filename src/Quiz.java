import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Adaptive console quiz.
 *
 * Scoring rule:
 * - Each player starts with a question value of 1 point.
 * - A correct answer adds the current question value to the score and
 *   increases the value of the next question by 1.
 * - An incorrect answer adds no points and resets the next question value to 1.
 *
 * Question set file format:
 * line 1: number of questions
 * then, for every question:
 *   question text
 *   answer text
 */
public class Quiz {

    private static final int PLAYER_COUNT = 2;
    private static final Scanner SCANNER = new Scanner(System.in);

    private record Question(String text, String answer) {}

    public static void main(String[] args) {
        try {
            showRules();

            if (askYesNo("Do you want to create a new question set? (yes/no): ")) {
                createNewQuestionSet();
            }

            Path questionSet = askForQuestionSet();
            List<Question> questions = loadQuestions(questionSet);
            runQuiz(questions);

        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid question-set file: " + e.getMessage());
        }
    }

    private static void showRules() {
        System.out.println("=== Adaptive Quiz ===");
        System.out.println("Rules:");
        System.out.println("- The first question is worth 1 point.");
        System.out.println("- After a correct answer, the next question is worth 1 more point.");
        System.out.println("- After a wrong answer, the next question resets to 1 point.");
        System.out.println("- The quiz is played by " + PLAYER_COUNT + " players.");
        System.out.println();
    }

    private static boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();

            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Please enter yes or no.");
        }
    }

    private static void createNewQuestionSet() throws IOException {
        System.out.print("Enter a file name (example: data/my-questions.txt): ");
        Path outputPath = Paths.get(SCANNER.nextLine().trim());

        int numberOfQuestions = readPositiveInteger("How many questions do you want to enter? ");

        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            System.out.println("\nQuestion " + (i + 1));
            System.out.print("Enter the question: ");
            String question = SCANNER.nextLine().trim();

            System.out.print("Enter the answer: ");
            String answer = SCANNER.nextLine().trim();

            if (question.isEmpty() || answer.isEmpty()) {
                System.out.println("Question and answer cannot be empty. Please enter this item again.");
                i--;
                continue;
            }

            questions.add(new Question(question, answer));
        }

        saveQuestions(outputPath, questions);
        System.out.println("Question set saved to: " + outputPath.toAbsolutePath());
        System.out.println();
    }

    private static int readPositiveInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Continue to validation message below.
            }

            System.out.println("Please enter a positive whole number.");
        }
    }

    private static void saveQuestions(Path outputPath, List<Question> questions) throws IOException {
        Path parent = outputPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            writer.write(String.valueOf(questions.size()));
            writer.newLine();

            for (Question question : questions) {
                writer.write(question.text());
                writer.newLine();
                writer.write(question.answer());
                writer.newLine();
            }
        }
    }

    private static Path askForQuestionSet() {
        System.out.print(
                "Enter the question set file to use " +
                "(example: data/months.txt or data/weekdays.txt): "
        );
        return Paths.get(SCANNER.nextLine().trim());
    }

    private static List<Question> loadQuestions(Path file) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String firstLine = reader.readLine();

            if (firstLine == null) {
                throw new IllegalArgumentException("The file is empty.");
            }

            int expectedCount;
            try {
                expectedCount = Integer.parseInt(firstLine.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The first line must contain the number of questions.");
            }

            if (expectedCount <= 0) {
                throw new IllegalArgumentException("The number of questions must be greater than zero.");
            }

            List<Question> questions = new ArrayList<>();

            for (int i = 0; i < expectedCount; i++) {
                String question = reader.readLine();
                String answer = reader.readLine();

                if (question == null || answer == null) {
                    throw new IllegalArgumentException(
                            "The file ended before all " + expectedCount + " questions were read."
                    );
                }

                questions.add(new Question(question, answer));
            }

            return questions;
        }
    }

    private static void runQuiz(List<Question> questions) {
        int[] totalScores = new int[PLAYER_COUNT];
        int[] nextQuestionPoints = new int[PLAYER_COUNT];

        for (int i = 0; i < PLAYER_COUNT; i++) {
            nextQuestionPoints[i] = 1;
        }

        for (int questionIndex = 0; questionIndex < questions.size(); questionIndex++) {
            Question question = questions.get(questionIndex);

            System.out.println("\n----------------------------------------");
            System.out.println("Question " + (questionIndex + 1) + " of " + questions.size());

            for (int playerIndex = 0; playerIndex < PLAYER_COUNT; playerIndex++) {
                System.out.println("\nPlayer " + (playerIndex + 1));
                System.out.println("Worth " + nextQuestionPoints[playerIndex] + " point(s).");
                System.out.println(question.text());
                System.out.print("Your answer: ");

                String userAnswer = SCANNER.nextLine().trim();
                boolean correct = userAnswer.equalsIgnoreCase(question.answer().trim());

                if (correct) {
                    totalScores[playerIndex] += nextQuestionPoints[playerIndex];
                    nextQuestionPoints[playerIndex]++;
                    System.out.println("Correct!");
                } else {
                    nextQuestionPoints[playerIndex] = 1;
                    System.out.println("Incorrect. Correct answer: " + question.answer());
                }

                System.out.println("Current score: " + totalScores[playerIndex]);
                System.out.println("Next question value: " + nextQuestionPoints[playerIndex]);
            }
        }

        showFinalScores(totalScores);
    }

    private static void showFinalScores(int[] totalScores) {
        System.out.println("\n========================================");
        System.out.println("Final scores");

        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < totalScores.length; i++) {
            System.out.println("Player " + (i + 1) + ": " + totalScores[i]);
            bestScore = Math.max(bestScore, totalScores[i]);
        }

        List<Integer> winners = new ArrayList<>();
        for (int i = 0; i < totalScores.length; i++) {
            if (totalScores[i] == bestScore) {
                winners.add(i + 1);
            }
        }

        if (winners.size() == 1) {
            System.out.println("Winner: Player " + winners.get(0));
        } else {
            System.out.println("Result: Draw between players " + winners);
        }
    }
}
