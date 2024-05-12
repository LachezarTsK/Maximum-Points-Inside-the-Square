
public class Solution {

    private static final int ALPHABET_SIZE = 26;
    private static final int DUPLICATE_LETTERS_FOUND = -1;
    private static final int[] CENTER_COORDINATES_ALL_SQUARES = {0, 0};
    private static final int MAX_ABSOLUTE_DISTANCE_FROM_CENTER = (int) Math.pow(10, 9);

    private final class Point {

        int minDistanceFromCenter = Integer.MAX_VALUE;
        int secondMinDistanceFromCenter = Integer.MAX_VALUE;
    }

    public int maxPointsInsideSquare(int[][] originalPoints, String letters) {
        Point[] points = createArrayPoints(originalPoints, letters);
        return findMaxPointsWithUniqueLettersInsideSquare(points);
    }

    private Point[] createArrayPoints(int[][] originalPoints, String letters) {
        Point[] points = new Point[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; ++i) {
            points[i] = new Point();
        }
        for (int i = 0; i < originalPoints.length; ++i) {
            updatePoint(points, originalPoints[i], letters.charAt(i));
        }
        return points;
    }

    private void updatePoint(Point[] points, int[] singleOriginalPoint, char letter) {
        int maxDistanceFromCenter = Math.max(Math.abs(singleOriginalPoint[0]), Math.abs(singleOriginalPoint[1]));

        if (points[letter - 'a'].minDistanceFromCenter > maxDistanceFromCenter) {
            points[letter - 'a'].secondMinDistanceFromCenter = points[letter - 'a'].minDistanceFromCenter;
            points[letter - 'a'].minDistanceFromCenter = maxDistanceFromCenter;
            return;
        }

        if (points[letter - 'a'].secondMinDistanceFromCenter > maxDistanceFromCenter) {
            points[letter - 'a'].secondMinDistanceFromCenter = maxDistanceFromCenter;
        }
    }

    private int findMaxPointsWithUniqueLettersInsideSquare(Point[] points) {
        int left = 0;
        int right = MAX_ABSOLUTE_DISTANCE_FROM_CENTER;
        int maxPointsInsideSquare = 0;

        while (left <= right) {
            int distanceFromCenter = left + (right - left) / 2;
            int countPoints = countPointsWithUniqueLetters(points, distanceFromCenter);

            if (countPoints == DUPLICATE_LETTERS_FOUND) {
                right = distanceFromCenter - 1;
            } else {
                left = distanceFromCenter + 1;
                maxPointsInsideSquare = Math.max(maxPointsInsideSquare, countPoints);
            }
        }

        return maxPointsInsideSquare;
    }

    private int countPointsWithUniqueLetters(Point[] points, int distanceFromCenter) {
        int countPointsWithUniqueLetters = 0;
        for (int i = 0; i < ALPHABET_SIZE; ++i) {
            if (points[i].secondMinDistanceFromCenter <= distanceFromCenter) {
                return DUPLICATE_LETTERS_FOUND;
            }
            if (points[i].minDistanceFromCenter <= distanceFromCenter) {
                ++countPointsWithUniqueLetters;
            }
        }
        return countPointsWithUniqueLetters;
    }
}
