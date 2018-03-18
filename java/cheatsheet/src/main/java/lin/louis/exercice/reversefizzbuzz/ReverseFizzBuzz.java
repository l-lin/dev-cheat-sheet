package lin.louis.exercice.reversefizzbuzz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author llin
 * @created 14/05/14.
 */
public class ReverseFizzBuzz {
    public static void main(String[] argv) throws Exception {
        String line;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            System.out.println(reverseFizzBuzz(line));
        }
    }

    public static String reverseFizzBuzz(String line) {
        String[] numbers = line.split(" ");

        List<Integer> indexList = fetchIndexList(numbers);
        List<Integer> answers = fetchAnswers(indexList);

        // Check just in case
        assert answers.size() == 2;

        return answers.get(0) + " " + answers.get(1);
    }

    private static List<Integer> fetchAnswers(List<Integer> indexList) {
        List<Integer> answerList = new ArrayList<>();
        for (Integer index : indexList) {
            if (!hasAMultiple(answerList, index)) {
                answerList.add(index);
            }
        }
        return answerList;
    }

    private static List<Integer> fetchIndexList(String[] numbers) {
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            String numberStr = numbers[i];

            if (!isNumber(numberStr)) {
                indexList.add(i + 1);
            }
        }

        return indexList;
    }

    private static boolean isNumber(String numberStr) {
        boolean isNumber = true;
        try {
            Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber;
    }

    private static boolean hasAMultiple(List<Integer> answerList, int index) {
        boolean hasAMultiple = false;
        for (Integer answer : answerList) {
            if (index % answer == 0) {
                hasAMultiple = true;
                break;
            }
        }

        return hasAMultiple;
    }
}
