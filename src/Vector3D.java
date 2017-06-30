/*
Copyright © 2015-2017 Leejae Karinja

This file is part of Java 3D Renderer.

Java 3D Renderer is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Java 3D Renderer is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Java 3D Renderer.  If not, see <http://www.gnu.org/licenses/>.
*/
public class Vector3D {
	public double x = 0;
	public double y = 0;
	public double z = 0;
	public double w = 0;
	Vector3D(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 1;
	}
	Vector3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}
	Vector3D(double x, double y, double z, double w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public void setX(double x){
		this.x = x;
		return;
	}
	public void setY(double y){
		this.y = y;
		return;
	}
	public void setZ(double z){
		this.z = z;
		return;
	}
	public void setW(double w){
		this.w = w;
		return;
	}
	public Vector3D normalize(){
		Vector3D vect = new Vector3D();
		vect.setX(this.x / getLength());
		vect.setY(this.y / getLength());
		vect.setZ(this.z / getLength());
		return vect;
	}
	private float getLength(){
		return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
}
