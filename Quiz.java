import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    private String question;
    private String[] options;
    private int correctAnswer;

    public QuizQuestion(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }
}

public class Quiz {
    private List<QuizQuestion> questions;
    private int score;
    private int currentQuestionIndex;
    private boolean answerSubmitted;
    private int[] userAnswers;

    public Quiz() {
        questions = new ArrayList<>();
        score = 0;
        currentQuestionIndex = 0;
        answerSubmitted = false;
        userAnswers = new int[questions.size()];

        // Sample questions
        questions.add(new QuizQuestion("What is the capital of France?", new String[]{"1. Paris", "2. London", "3. Berlin", "4. Madrid"}, 1));
        questions.add(new QuizQuestion("Who wrote 'Hamlet'?", new String[]{"1. Charles Dickens", "2. William Shakespeare", "3. J.K. Rowling", "4. Mark Twain"}, 2));
        questions.add(new QuizQuestion("What is the smallest planet in our solar system?", new String[]{"1. Earth", "2. Venus", "3. Mars", "4. Mercury"}, 4));

        // Initialize userAnswers array with -1 to indicate no answer
        userAnswers = new int[questions.size()];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (currentQuestionIndex = 0; currentQuestionIndex < questions.size(); currentQuestionIndex++) {
            QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
            System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestion());

            String[] options = currentQuestion.getOptions();
            for (String option : options) {
                System.out.println(option);
            }

            answerSubmitted = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!answerSubmitted) {
                        System.out.println("\nTime is up!");
                        submitAnswer(-1); // No answer submitted within the time limit
                    }
                }
            }, 10000); // 10 seconds for each question

            System.out.print("Enter your answer (1-4): ");
            int answer = scanner.nextInt();
            answerSubmitted = true;
            timer.cancel();
            submitAnswer(answer);
        }

        displayResults();
        scanner.close();
    }

    private void submitAnswer(int answer) {
        QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
        userAnswers[currentQuestionIndex] = answer;

        if (currentQuestion.isCorrect(answer)) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect. The correct answer was " + (currentQuestion.getCorrectAnswer()));
        }
    }

    private void displayResults() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score is: " + score + "/" + questions.size());
        System.out.println("Summary of correct/incorrect answers:");

        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestion());

            if (userAnswers[i] == -1) {
                System.out.println("Your answer: Not Answered");
            } else if (question.isCorrect(userAnswers[i])) {
                System.out.println("Your answer: Correct");
            } else {
                System.out.println("Your answer: Incorrect");
            }
        }
    }

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.start();
    }
}
