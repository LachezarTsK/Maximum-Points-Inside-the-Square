
#include <span>
#include <cmath>
#include <array>
#include <vector>
#include <limits>
#include <string>
#include <algorithm>
using namespace std;

class Solution {

    struct Point {
        // alternatively: INT_MAX
        int minDistanceFromCenter = numeric_limits<int>::max();
        int secondMinDistanceFromCenter = numeric_limits<int>::max();
    };

    static const int ALPHABET_SIZE = 26;
    static const int DUPLICATE_LETTERS_FOUND = -1;
    static constexpr array<int, 2> CENTER_COORDINATES_ALL_SQUARES = { 0, 0 };
    static constexpr int MAX_ABSOLUTE_DISTANCE_FROM_CENTER = 1000 * 1000 * 1000;

public:
    int maxPointsInsideSquare(const vector<vector<int>>& originalPoints, const string& letters)const {
        array<Point, ALPHABET_SIZE>  points = createArrayPoints(originalPoints, letters);
        return findMaxPointsWithUniqueLettersInsideSquare(points);
    }

private:
    array<Point, ALPHABET_SIZE>  createArrayPoints(span<const vector<int>> originalPoints, string_view letters) const {
        array<Point, ALPHABET_SIZE> points{};
        for (size_t i = 0; i < originalPoints.size(); ++i) {
            updatePoint(points, originalPoints[i], letters[i]);
        }
        return points;
    }

    void updatePoint(span<Point> points, span<const int> singleOriginalPoint, char letter) const {
        int maxDistanceFromCenter = max(abs(singleOriginalPoint[0]), abs(singleOriginalPoint[1]));

        if (points[letter - 'a'].minDistanceFromCenter > maxDistanceFromCenter) {
            points[letter - 'a'].secondMinDistanceFromCenter = points[letter - 'a'].minDistanceFromCenter;
            points[letter - 'a'].minDistanceFromCenter = maxDistanceFromCenter;
            return;
        }

        if (points[letter - 'a'].secondMinDistanceFromCenter > maxDistanceFromCenter) {
            points[letter - 'a'].secondMinDistanceFromCenter = maxDistanceFromCenter;
        }
    }

    int findMaxPointsWithUniqueLettersInsideSquare(span<const Point> points) const {
        int left = 0;
        int right = MAX_ABSOLUTE_DISTANCE_FROM_CENTER;
        int maxPointsInsideSquare = 0;

        while (left <= right) {
            int distanceFromCenter = left + (right - left) / 2;
            int countPoints = countPointsWithUniqueLetters(points, distanceFromCenter);

            if (countPoints == DUPLICATE_LETTERS_FOUND) {
                right = distanceFromCenter - 1;
            }
            else {
                left = distanceFromCenter + 1;
                maxPointsInsideSquare = max(maxPointsInsideSquare, countPoints);
            }
        }

        return maxPointsInsideSquare;
    }

    int countPointsWithUniqueLetters(span<const Point> points, int distanceFromCenter) const {
        int countPointsWithUniqueLetters = 0;
        for (size_t i = 0; i < ALPHABET_SIZE; ++i) {
            if (points[i].secondMinDistanceFromCenter <= distanceFromCenter) {
                return DUPLICATE_LETTERS_FOUND;
            }
            if (points[i].minDistanceFromCenter <= distanceFromCenter) {
                ++countPointsWithUniqueLetters;
            }
        }
        return countPointsWithUniqueLetters;
    }
};
