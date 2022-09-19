import java.util.HashSet;
import java.util.Scanner;

public class Main {
    final static int MAX_CODE_LENGTH = 36;
    static final int FIRST_INAPPROPRIATE_CHAR = 58;
    static final int LAST_INAPPROPRIATE_CHAR = 64;
    static final int FIRST_APPROPRIATE_CHAR = 48;
    final int LAST_APPROPRIATE_CHAR = 90;

    public static void main(String[] args) {
        final int min = 1000;
        final int max = 9999;
        String secretCode;
        boolean checker = false;
        int turnNumber = 0;
        int[] playerInput = new int[2];
        String playerAnswer;
        boolean isVictory = false;
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Please, enter the secret code's length:");
            playerInput[0] = Integer.parseInt(scanner.nextLine());
            System.out.println("Please, enter the secret code's difficulty (from 1 to 36): ");
            playerInput[1] = Integer.parseInt(scanner.nextLine());
            secretCode = generateSecretCodeOrNull(playerInput[0], playerInput[1]);
            if (secretCode != null) {
                codePreview(secretCode.length(), playerInput[1]);
                System.out.println("Okay, let's start the game!");
                while (!isVictory) {
                    System.out.println("Turn " + ++turnNumber + ":");
                    playerAnswer = scanner.nextLine();
                    isVictory = defineBullsAndCows(secretCode, playerAnswer);
                }
                System.out.println("Congratulations! You guessed the secret code.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: something is wrong with your input");
        }
    }

    private static String generateSecretCodeOrNull(int length, int numberOfDifferentChars) {
        HashSet<String> usedChars = new HashSet<>();
        StringBuilder resultBuilder = new StringBuilder();
        char currentChar;
        boolean checker;

        if (length > MAX_CODE_LENGTH || length > numberOfDifferentChars || length <= 0) {
            System.out.println("Error: can't generate a secret number with a length of " + length + ".");
            return null;
        } else if(numberOfDifferentChars>MAX_CODE_LENGTH){
            System.out.println("Error: can't generate a secret number with a difficulty of " + numberOfDifferentChars + ".");
            return null;
        }else{
            currentChar = randomChar(numberOfDifferentChars);
            usedChars.add(String.valueOf(currentChar));
            resultBuilder.append(currentChar);
            for (int i = 0; i < length - 1; i++) {
                checker = false;
                while (!checker) {
                    currentChar = randomChar(numberOfDifferentChars);
                    if (!usedChars.contains(String.valueOf(currentChar))) {
                        checker = true;
                        resultBuilder.append(currentChar);
                        usedChars.add(String.valueOf(currentChar));
                    }
                }
            }
            return resultBuilder.toString().toLowerCase();
        }
    }

    private static boolean defineBullsAndCows(String code, String playerAnswer) {
        final String codeConst = code;
        final String playerAnswerConst = playerAnswer;
        byte bulls = 0;
        byte cows = 0;
        int length = playerAnswer.length();
        HashSet<String> charsOfCode = new HashSet<>();

        while (code.length() > 0) {
            charsOfCode.add(code.substring(code.length() - 1));
            code = code.substring(0, code.length() - 1);
        }
        code = codeConst;
        while (playerAnswer.length() >= 1) {
            String currentChar;
            if (playerAnswer.length() > 1) {
                currentChar = playerAnswer.substring(playerAnswer.length() - 1);
            } else {
                currentChar = playerAnswer;
            }
            if (currentChar.equals(code.substring(code.length() - 1))) {
                bulls++;
            } else if (charsOfCode.contains(currentChar)) {
                cows++;
            }
            code = code.substring(0, playerAnswer.length() - 1);
            playerAnswer = playerAnswer.substring(0, playerAnswer.length() - 1);
        }

        if (bulls == 0 && cows == 0) {
            System.out.println("Grade: None.");
        } else if ((bulls != 0 && cows == 0) || bulls == length) {
            System.out.println("Grade: " + bulls + " bull(s).");
        } else if (bulls == 0 && cows != 0) {
            System.out.println("Grade: " + cows + " cow(s).");
        } else {
            System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s).");
        }
        return bulls == length;
    }

    private static char randomChar(int to) {
        final int NUMBER_OF_INAPPROPRIATE_CHARS = LAST_INAPPROPRIATE_CHAR - FIRST_INAPPROPRIATE_CHAR + 1;
        int result;

        to = FIRST_APPROPRIATE_CHAR + to - 1;
        result = (int) (Math.random() * (to - FIRST_APPROPRIATE_CHAR + 1) + FIRST_APPROPRIATE_CHAR);
        if (result >= FIRST_INAPPROPRIATE_CHAR && result <= LAST_INAPPROPRIATE_CHAR) {
            result += NUMBER_OF_INAPPROPRIATE_CHARS;
        }
        return (char) result;
    }

    private static void codePreview(int codeLength, int codeDifficulty) {
        StringBuilder stringBuilder = new StringBuilder("The secret is prepared: ");
        stringBuilder.append("*".repeat(codeLength));
        if (codeDifficulty <= 10) {
            stringBuilder.append(" (0-").append((char) (FIRST_APPROPRIATE_CHAR + codeDifficulty - 1)).append(")");
        } else {
            stringBuilder.append(" (0-9, " + (char) (LAST_INAPPROPRIATE_CHAR + 1) + "-").append((char) (LAST_INAPPROPRIATE_CHAR + codeDifficulty - 10)).append(")");
        }
        System.out.println(stringBuilder.toString().toLowerCase());
    }
}













































