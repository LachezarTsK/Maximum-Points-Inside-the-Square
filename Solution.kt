
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow

class Solution {

    private companion object {
        const val ALPHABET_SIZE = 26
        const val DUPLICATE_LETTERS_FOUND = -1
        val CENTER_COORDINATES_ALL_SQUARES = intArrayOf(0, 0)
        val MAX_ABSOLUTE_DISTANCE_FROM_CENTER = 10.0.pow(9).toInt()
    }

    private class Point {
        var minDistanceFromCenter = Int.MAX_VALUE
        var secondMinDistanceFromCenter = Int.MAX_VALUE
    }

    fun maxPointsInsideSquare(originalPoints: Array<IntArray>, letters: String): Int {
        val points = createArrayPoints(originalPoints, letters)
        return findMaxPointsWithUniqueLettersInsideSquare(points)
    }

    private fun createArrayPoints(originalPoints: Array<IntArray>, letters: String): ArrayList<Point> {
        val points = ArrayList<Point>(ALPHABET_SIZE)
        for (i in 0..<ALPHABET_SIZE) {
            points.add(Point())
        }
        for (i in originalPoints.indices) {
            updatePoint(points, originalPoints[i], letters[i])
        }
        return points
    }

    private fun updatePoint(points: ArrayList<Point>, singleOriginalPoint: IntArray, letter: Char) {
        val maxDistanceFromCenter = max(abs(singleOriginalPoint[0]), abs(singleOriginalPoint[1]))

        if (points[letter - 'a'].minDistanceFromCenter > maxDistanceFromCenter) {
            points[letter - 'a'].secondMinDistanceFromCenter = points[letter - 'a'].minDistanceFromCenter
            points[letter - 'a'].minDistanceFromCenter = maxDistanceFromCenter
            return
        }

        if (points[letter - 'a'].secondMinDistanceFromCenter > maxDistanceFromCenter) {
            points[letter - 'a'].secondMinDistanceFromCenter = maxDistanceFromCenter
        }
    }

    private fun findMaxPointsWithUniqueLettersInsideSquare(points: ArrayList<Point>): Int {
        var left = 0
        var right = MAX_ABSOLUTE_DISTANCE_FROM_CENTER
        var maxPointsInsideSquare = 0

        while (left <= right) {
            val distanceFromCenter = left + (right - left) / 2
            val countPoints = countPointsWithUniqueLetters(points, distanceFromCenter)

            if (countPoints == DUPLICATE_LETTERS_FOUND) {
                right = distanceFromCenter - 1
            } else {
                left = distanceFromCenter + 1
                maxPointsInsideSquare = max(maxPointsInsideSquare, countPoints)
            }
        }

        return maxPointsInsideSquare
    }

    private fun countPointsWithUniqueLetters(points: ArrayList<Point>, distanceFromCenter: Int): Int {
        var countPointsWithUniqueLetters = 0
        for (i in 0..<ALPHABET_SIZE) {
            if (points[i].secondMinDistanceFromCenter <= distanceFromCenter) {
                return DUPLICATE_LETTERS_FOUND
            }
            if (points[i].minDistanceFromCenter <= distanceFromCenter) {
                ++countPointsWithUniqueLetters
            }
        }
        return countPointsWithUniqueLetters
    }
}
