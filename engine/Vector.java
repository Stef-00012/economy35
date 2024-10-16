package engine;

public final class Vector {
    public final double x;
    public final double y;

    //#region Default Vectors
    public static Vector ZERO = new Vector();
    public static Vector ONE = new Vector(1, 1);

    public static Vector LEFT = new Vector(-1, 0);
    public static Vector RIGHT = new Vector(1, 0);

    public static Vector UP = new Vector(0, 1);
    public static Vector DOWN = new Vector(0, -1);
    //#endregion

    public Vector(double x, double y){
        this.x = x; 
        this.y = y;
    }
    public Vector(){
        this.x = 0; 
        this.y = 0;
    }
    public Vector(double value){
        this.x = value; 
        this.y = value;
    }

    //#region Operation methods
    /**
     * Adds two vectors.
     * @param v1 - The first vector.
     * @param v2 - The second vector.
     * @return A new vector representing the sum of the input vectors.
     */
    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.x + v2.x, v1.y + v2.y);
    }

    /**
     * Adds another vector to this vector.
     * @param v - The vector to add.
     * @return A new vector representing the sum.
     */
    public Vector add(Vector v) {
        return Vector.add(this, v);
    }

    /**
     * Subtracts the second vector from the first vector.
     * @param v1 - The first vector.
     * @param v2 - The second vector.
     * @return A new vector representing the result of the subtraction.
     */
    public static Vector subtract(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Subtracts another vector from this vector.
     * @param v - The vector to subtract.
     * @return A new vector representing the result of the subtraction.
     */
    public Vector subtract(Vector v) {
        return Vector.subtract(this, v);
    }

    /**
     * Multiplies a vector by a scalar.
     * @param v - The vector.
     * @param scalar - The scalar value.
     * @return A new vector representing the result of the multiplication.
     */
    public static Vector multiply(Vector v, double scalar) {
        return new Vector(v.x * scalar, v.y * scalar);
    }

    /**
     * Multiplies this vector by a scalar.
     * @param scalar - The scalar value.
     * @return A new vector representing the result of the multiplication.
     */
    public Vector multiply(double scalar) {
        return Vector.multiply(this, scalar);
    }

    /**
     * Divides a vector by a scalar.
     * @param v - The vector.
     * @param scalar - The scalar value.
     * @return A new vector representing the result of the division.
     */
    public static Vector divide(Vector v, double scalar) {
        return new Vector(v.x / scalar, v.y / scalar);
    }

    /**
     * Divides this vector by a scalar.
     * @param scalar - The scalar value.
     * @return A new vector representing the result of the division.
     */
    public Vector divide(double scalar) {
        return Vector.divide(this, scalar);
    }

    /**
     * Divides one vector by another vector component-wise.
     * @param v1 - The numerator vector.
     * @param v2 - The denominator vector.
     * @return A new vector representing the result of the component-wise division.
     */
    public static Vector divideVec(Vector v1, Vector v2) {
        return new Vector(v1.x / v2.x, v1.y / v2.y);
    }

    /**
     * Multiplies one vector by another vector component-wise.
     * @param v1 - The first vector.
     * @param v2 - The second vector.
     * @return A new vector representing the result of the component-wise multiplication.
     */
    public static Vector multiplyVec(Vector v1, Vector v2) {
        return new Vector(v1.x * v2.x, v1.y * v2.y);
    }
    //#endregion

    /**
     * Linearly interpolates between two vectors.
     *
     * @param v1 - The starting vector.
     * @param v2 - The ending vector.
     * @param t - The interpolation factor. Should be a value between 0 and 1.
     * @return A new vector representing the result of the linear interpolation.
     */
    public static Vector lerp(Vector v1, Vector v2, double t) {
        return new Vector(
            Utils.lerp(v1.x, v2.x, t),    // Interpolate x component
            Utils.lerp(v1.y, v2.y, t)     // Interpolate y component
        );
    }

    /**
     * Calculates the distance between two vectors.
     * @param v1 - The first vector.
     * @param v2 - The second vector.
     * @return The distance between the two vectors.
     */
    public static double distance(Vector v1, Vector v2) {
        return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
    }

    /**
     * Calculates the dot product between two vectors.
     * @param v1 - The first vector.
     * @param v2 - The second vector.
     * @return The dot product between the two vectors.
     */
    public static double dot(Vector v1, Vector v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /**
     * Calculates the dot product between this vector and another vector.
     * @param v - The other vector.
     * @return The dot product between this vector and the other vector.
     */
    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * Calculates the length (magnitude) of this vector.
     * @return The length (magnitude) of this vector.
     */
    public double len() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Normalizes this vector (converts it to a unit vector).
     * @return A new Vector instance representing the normalized vector.
     * @throws IllegalArgumentException if attempting to normalize a zero vector.
     */
    public Vector normalize() {
        double length = this.len();
        if (length == 0) {
            throw new IllegalArgumentException("Cannot normalize a zero vector.");
        }
        return new Vector(this.x / length, this.y / length);
    }

    /**
    * Rotates the vector by the given angle (in radians) around the origin.
    * @param angle - The angle (in radians) by which to rotate the vector.
    * @return A new Vector instance representing the rotated vector.
    */
    public Vector rotate(double angle) {
        return new Vector(
            this.x * Math.cos(angle) - this.y * Math.sin(angle),
            this.x * Math.sin(angle) + this.y * Math.cos(angle)
        );
    }

    /**
     * Casts the x variable to an integer
     * @return The x of the vector as an integer 
     */
    public int getX(){
        return (int)x;
    }

    /**
     * Casts the y variable to an integer
     * @return The y of the vector as an integer 
     */
    public int getY(){
        return (int)x;
    }
}


