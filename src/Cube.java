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
import java.awt.Graphics;

public class Cube {
	protected Vector3D s0 = new Vector3D();
	protected Vector3D s1 = new Vector3D();// = new Vector3D(10, 0, 0);
	protected Vector3D s2 = new Vector3D();// = new Vector3D(0, 10, 0);
	protected Vector3D s3 = new Vector3D();// = new Vector3D(0, 0, 10);
	protected Vector3D s4 = new Vector3D();// = new Vector3D(10, 10, 0);
	protected Vector3D s5 = new Vector3D();// = new Vector3D(0, 10, 10);
	protected Vector3D s6 = new Vector3D();// = new Vector3D(10, 0, 10);
	protected Vector3D s7 = new Vector3D();// = new Vector3D(10, 10, 10);
	Cube(int x, int y, int z, int len){
		s0.x = x;
		s0.y = y;
		s0.z = z;

		s1.x = x + len;
		s1.y = y;
		s1.z = z;

		s2.x = x;
		s2.y = y + len;
		s2.z = z;

		s3.x = x;
		s3.y = y;
		s3.z = z + len;

		s4.x = x + len;
		s4.y = y + len;
		s4.z = z;

		s5.x = x;
		s5.y = y + len;
		s5.z = z + len;

		s6.x = x + len;
		s6.y = y;
		s6.z = z + len;

		s7.x = x + len;
		s7.y = y + len;
		s7.z = z + len;
	}
	public void draw(Graphics g){
		Renderer.drawLine(g, s0, s1);
		Renderer.drawLine(g, s1, s4);
		Renderer.drawLine(g, s4, s2);
		Renderer.drawLine(g, s2, s0);
		Renderer.drawLine(g, s0, s3);
		Renderer.drawLine(g, s3, s5);
		Renderer.drawLine(g, s5, s7);
		Renderer.drawLine(g, s7, s4);
		Renderer.drawLine(g, s5, s2);
		Renderer.drawLine(g, s7, s6);
		Renderer.drawLine(g, s6, s3);
		Renderer.drawLine(g, s6, s1);
		return;
	}
}
