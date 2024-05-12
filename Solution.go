
package main

import (
	"fmt"
	"math"
)

const ALPHABET_SIZE = 26
const DUPLICATE_LETTERS_FOUND = -1

var CENTER_COORDINATES_ALL_SQUARES = [2]int{0, 0}
var MAX_ABSOLUTE_DISTANCE_FROM_CENTER = int(math.Pow(10.0, 9.0))

type Point struct {
	MinDistanceFromCenter       int
	SecondMinDistanceFromCenter int
}

func NewPoint() *Point {
	point := Point{}
	point.MinDistanceFromCenter = math.MaxInt
	point.SecondMinDistanceFromCenter = math.MaxInt
	return &point
}

func maxPointsInsideSquare(originalPoints [][]int, letters string) int {
	var points = createArrayPoints(originalPoints, letters)
	return findMaxPointsWithUniqueLettersInsideSquare(&points)
}

func createArrayPoints(originalPoints [][]int, letters string) [ALPHABET_SIZE]Point {
	points := [ALPHABET_SIZE]Point{}

	for i := 0; i < ALPHABET_SIZE; i++ {
		points[i] = *(NewPoint())
	}
	for i := 0; i < len(originalPoints); i++ {
		updatePoint(&points, originalPoints[i], letters[i])
	}
	return points
}

func updatePoint(points *[ALPHABET_SIZE]Point, singleOriginalPoint []int, letter byte) {
	var maxDistanceFromCenter = int(math.Max(math.Abs(float64(singleOriginalPoint[0])), math.Abs(float64(singleOriginalPoint[1]))))

	if points[letter-'a'].MinDistanceFromCenter > maxDistanceFromCenter {
		points[letter-'a'].SecondMinDistanceFromCenter = points[letter-'a'].MinDistanceFromCenter
		points[letter-'a'].MinDistanceFromCenter = maxDistanceFromCenter
		return
	}

	if points[letter-'a'].SecondMinDistanceFromCenter > maxDistanceFromCenter {
		points[letter-'a'].SecondMinDistanceFromCenter = maxDistanceFromCenter
	}
}

func findMaxPointsWithUniqueLettersInsideSquare(points *[ALPHABET_SIZE]Point) int {
	var left = 0
	var right = MAX_ABSOLUTE_DISTANCE_FROM_CENTER
	var maxPointsInsideSquare = 0

	for left <= right {
		var distanceFromCenter = left + (right-left)/2
		var countPoints = countPointsWithUniqueLetters(points, distanceFromCenter)

		if countPoints == DUPLICATE_LETTERS_FOUND {
			right = distanceFromCenter - 1
		} else {
			left = distanceFromCenter + 1
			maxPointsInsideSquare = int(math.Max(float64(maxPointsInsideSquare), float64(countPoints)))
		}
	}

	return maxPointsInsideSquare
}

func countPointsWithUniqueLetters(points *[ALPHABET_SIZE]Point, distanceFromCenter int) int {
	var countPointsWithUniqueLetters = 0
	for i := 0; i < ALPHABET_SIZE; i++ {
		if points[i].SecondMinDistanceFromCenter <= distanceFromCenter {
			return DUPLICATE_LETTERS_FOUND
		}
		if points[i].MinDistanceFromCenter <= distanceFromCenter {
			countPointsWithUniqueLetters++
		}
	}
	return countPointsWithUniqueLetters
}
