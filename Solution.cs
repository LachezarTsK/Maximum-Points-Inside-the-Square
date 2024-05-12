
using System;

public class Solution
{
    private static readonly int ALPHABET_SIZE = 26;
    private static readonly int DUPLICATE_LETTERS_FOUND = -1;
    private static readonly int[] CENTER_COORDINATES_ALL_SQUARES = { 0, 0 };
    private static readonly int MAX_ABSOLUTE_DISTANCE_FROM_CENTER = (int)Math.Pow(10, 9);

    private sealed class Point
    {
        public int minDistanceFromCenter = int.MaxValue;
        public int secondMinDistanceFromCenter = int.MaxValue;
    }
    public int MaxPointsInsideSquare(int[][] originalPoints, string letters)
    {
        Point[] points = CreateArrayPoints(originalPoints, letters);
        return FindMaxPointsWithUniqueLettersInsideSquare(points);
    }

    private Point[] CreateArrayPoints(int[][] originalPoints, String letters)
    {
        Point[] points = new Point[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; ++i)
        {
            points[i] = new Point();
        }
        for (int i = 0; i < originalPoints.Length; ++i)
        {
            UpdatePoint(points, originalPoints[i], letters[i]);
        }
        return points;
    }

    private void UpdatePoint(Point[] points, int[] singleOriginalPoint, char letter)
    {
        int maxDistanceFromCenter = Math.Max(Math.Abs(singleOriginalPoint[0]), Math.Abs(singleOriginalPoint[1]));

        if (points[letter - 'a'].minDistanceFromCenter > maxDistanceFromCenter)
        {
            points[letter - 'a'].secondMinDistanceFromCenter = points[letter - 'a'].minDistanceFromCenter;
            points[letter - 'a'].minDistanceFromCenter = maxDistanceFromCenter;
            return;
        }

        if (points[letter - 'a'].secondMinDistanceFromCenter > maxDistanceFromCenter)
        {
            points[letter - 'a'].secondMinDistanceFromCenter = maxDistanceFromCenter;
        }
    }

    private int FindMaxPointsWithUniqueLettersInsideSquare(Point[] points)
    {
        int left = 0;
        int right = MAX_ABSOLUTE_DISTANCE_FROM_CENTER;
        int maxPointsInsideSquare = 0;

        while (left <= right)
        {
            int distanceFromCenter = left + (right - left) / 2;
            int countPoints = CountPointsWithUniqueLetters(points, distanceFromCenter);

            if (countPoints == DUPLICATE_LETTERS_FOUND)
            {
                right = distanceFromCenter - 1;
            }
            else
            {
                left = distanceFromCenter + 1;
                maxPointsInsideSquare = Math.Max(maxPointsInsideSquare, countPoints);
            }
        }

        return maxPointsInsideSquare;
    }

    private int CountPointsWithUniqueLetters(Point[] points, int distanceFromCenter)
    {
        int countPointsWithUniqueLetters = 0;
        for (int i = 0; i < ALPHABET_SIZE; ++i)
        {
            if (points[i].secondMinDistanceFromCenter <= distanceFromCenter)
            {
                return DUPLICATE_LETTERS_FOUND;
            }
            if (points[i].minDistanceFromCenter <= distanceFromCenter)
            {
                ++countPointsWithUniqueLetters;
            }
        }
        return countPointsWithUniqueLetters;
    }
}
