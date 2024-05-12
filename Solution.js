
/**
 * @param {number[][]} originalPoints
 * @param {string} letters
 * @return {number}
 */
var maxPointsInsideSquare = function (originalPoints, letters) {
    this.ALPHABET_SIZE = 26;
    this.DUPLICATE_LETTERS_FOUND = -1;
    this.CENTER_COORDINATES_ALL_SQUARES = [0, 0];
    this.MAX_ABSOLUTE_DISTANCE_FROM_CENTER = Math.pow(10, 9);

    const points = createArrayPoints(originalPoints, letters);
    return findMaxPointsWithUniqueLettersInsideSquare(points);

};

class Point {

    minDistanceFromCenter = Number.MAX_SAFE_INTEGER;
    secondMinDistanceFromCenter = Number.MAX_SAFE_INTEGER;
}

/**
 * @param {number[][]} originalPoints
 * @param {string} letters
 * @return {Point[]}
 */
function createArrayPoints(originalPoints, letters) {
    const points = new Array(this.ALPHABET_SIZE);
    for (let i = 0; i < this.ALPHABET_SIZE; ++i) {
        points[i] = new Point();
    }
    for (let i = 0; i < originalPoints.length; ++i) {
        updatePoint(points, originalPoints[i], letters.charAt(i));
    }
    return points;
}

/**
 * @param {Point[]} points
 * @param {number[]} singleOriginalPoint
 * @param {string} letter
 * @return {void}
 */
function updatePoint(points, singleOriginalPoint, letter) {
    let maxDistanceFromCenter = Math.max(Math.abs(singleOriginalPoint[0]), Math.abs(singleOriginalPoint[1]));

    if (points[getIndex(letter)].minDistanceFromCenter > maxDistanceFromCenter) {
        points[getIndex(letter)].secondMinDistanceFromCenter = points[getIndex(letter)].minDistanceFromCenter;
        points[getIndex(letter)].minDistanceFromCenter = maxDistanceFromCenter;
        return;
    }

    if (points[getIndex(letter)].secondMinDistanceFromCenter > maxDistanceFromCenter) {
        points[getIndex(letter)].secondMinDistanceFromCenter = maxDistanceFromCenter;
    }
}

/**
 * @param {Point[]} points
 * @return {number}
 */
function findMaxPointsWithUniqueLettersInsideSquare(points) {
    let left = 0;
    let right = this.MAX_ABSOLUTE_DISTANCE_FROM_CENTER;
    let maxPointsInsideSquare = 0;

    while (left <= right) {
        let distanceFromCenter = left + Math.floor((right - left) / 2);
        let countPoints = countPointsWithUniqueLetters(points, distanceFromCenter);

        if (countPoints === this.DUPLICATE_LETTERS_FOUND) {
            right = distanceFromCenter - 1;
        } else {
            left = distanceFromCenter + 1;
            maxPointsInsideSquare = Math.max(maxPointsInsideSquare, countPoints);
        }
    }

    return maxPointsInsideSquare;
}

/**
 * @param {Point[]} points
 * @param {number} distanceFromCenter
 * @return {number}
 */
function countPointsWithUniqueLetters(points, distanceFromCenter) {
    let countPointsWithUniqueLetters = 0;
    for (let i = 0; i < this.ALPHABET_SIZE; ++i) {
        if (points[i].secondMinDistanceFromCenter <= distanceFromCenter) {
            return this.DUPLICATE_LETTERS_FOUND;
        }
        if (points[i].minDistanceFromCenter <= distanceFromCenter) {
            ++countPointsWithUniqueLetters;
        }
    }
    return countPointsWithUniqueLetters;
}

/**
 * @param {string} letter
 * @return {number}
 */
function getIndex(letter) {
    return letter.codePointAt(0) - 'a'.codePointAt(0);
}
