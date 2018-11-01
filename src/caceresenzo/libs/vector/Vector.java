package caceresenzo.libs.vector;

public class Vector {
	
	public float x, y, z;
	
	public Vector() {
		this(0, 0, 0);
	}
	
	public Vector(float x, float y) {
		this(x, y, 0);
	}
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector add(Vector vector) {
		x += vector.x;
		y += vector.y;
		z += vector.z;
		
		return this;
	}
	
	public Vector sub(Vector vector) {
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		
		return this;
	}
	
	public Vector div(float number) {
		x /= number;
		y /= number;
		z /= number;
		
		return this;
	}
	
	public Vector mult(float number) {
		x *= number;
		y *= number;
		z *= number;
		
		return this;
	}
	
	public Vector limit(float max) {
		if (magSq() > max * max) {
			normalize();
			mult(max);
		}
		
		return this;
	}
	
	public float mag() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float magSq() {
		return (x * x + y * y + z * z);
	}
	
	public Vector normalize() {
		float magnitude = mag();
		
		if (magnitude != 0 && magnitude != 1) {
			div(magnitude);
		}
		
		return this;
	}
	
	public Vector normalize(Vector target) {
		if (target == null) {
			target = new Vector();
		}
		
		float magnetude = mag();
		
		if (magnetude > 0) {
			target.set(x / magnetude, y / magnetude, z / magnetude);
		} else {
			target.set(x, y, z);
		}
		
		return target;
	}
	
	public Vector set(float x, float y) {
		this.x = x;
		this.y = y;
		
		return this;
	}
	
	public Vector set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getZ() {
		return z;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "Vector[x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	public static float dist(float x1, float y1, float x2, float y2) {
		float dx = x1 - x2;
		float dy = y1 - y2;
		
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
	
	public static float dist(Vector vector1, Vector vector2) {
		float dx = vector1.x - vector2.x;
		float dy = vector1.y - vector2.y;
		float dz = vector1.z - vector2.z;
		
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public Vector set(Vector vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
		
		return this;
	}
	
}