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
public class Main {
	protected static final float FOV = (float) (1.0 / (float)(Math.tan(Math.toRadians(1.0 /*###.# Degree FOV*/ / 2.0))));
	protected static final short HEIGHT = 600;
	protected static final short WIDTH = 800;
	public static void main(String[] args) {
		Renderer r = new Renderer();
		r.addCube(0, 0, 0, 10);
		return;
	}

}
