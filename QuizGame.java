import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizGame {
    private static final int QUESTION_TIME_LIMIT = 15; // time limit in seconds per question
    private static final Scanner scanner = new Scanner(System.in);
    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static boolean answered = false;

    private static class Question {
        String question;
        String[] options;
        int correctAnswer;

        Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    private static final Question[] questions = {
        new Question("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3),
        new Question("Which planet is known as the Red Planet?", new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Venus"}, 2),
        new Question("Who wrote 'Romeo and Juliet'?", new String[]{"1. William Shakespeare", "2. Mark Twain", "3. Charles Dickens", "4. Jane Austen"}, 1),
        new Question("What is the largest ocean on Earth?", new String[]{"1. Atlantic", "2. Indian", "3. Arctic", "4. Pacific"}, 4),
        new Question("Which element has the chemical symbol 'O'?", new String[]{"1. Oxygen", "2. Gold", "3. Silver", "4. Helium"}, 1)
    };

    public static void main(String[] args) {
        for (currentQuestionIndex = 0; currentQuestionIndex < questions.length; currentQuestionIndex++) {
            answered = false;
            displayQuestion(currentQuestionIndex);
            startTimer();
            waitForAnswerOrTimeout();
        }

        displayResults();
    }

    private static void displayQuestion(int index) {
        Question question = questions[index];
        System.out.println("\nQuestion " + (index + 1) + ": " + question.question);
        for (String option : question.options) {
            System.out.println(option);
        }
        System.out.print("Please enter the option number (1-4): ");
    }

    private static void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!answered) {
                    System.out.println("\nTime's up! Moving to the next question.");
                    answered = true;
                }
            }
        }, QUESTION_TIME_LIMIT * 1000);
    }

    private static void waitForAnswerOrTimeout() {
        while (!answered) {
            if (scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                answered = true;
                checkAnswer(answer);
            }
        }
    }

    private static void checkAnswer(int answer) {
        if (answer == questions[currentQuestionIndex].correctAnswer) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was: " + questions[currentQuestionIndex].correctAnswer);
        }
    }

    private static void displayResults() {
        System.out.println("\nQuiz over!");
        System.out.println("Your score: " + score + "/" + questions.length);
        System.out.println("Summary of correct and incorrect answers:");

        for (int i = 0; i < questions.length; i++) {
            String result = (questions[i].correctAnswer == (scanner.hasNextInt() ? scanner.nextInt() : -1)) ? "Correct" : "Incorrect";
            System.out.println("Question " + (i + 1) + ": " + result);
        }
    }
}
