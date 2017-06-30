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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Renderer extends JFrame{
	private static final long serialVersionUID = 1L;
	protected static double[] matrix = new double[16];
	protected static double aspectRatio = Main.WIDTH / Main.HEIGHT; 
	protected static double near = 5.0;
	protected static double far = 50.0;
	protected static double movement = 1.0;
	protected static boolean flight = true;
	protected static Vector3D viewpoint = new Vector3D(0, 0, 0);
	protected static Vector3D viewangle = new Vector3D(0, 0, Math.PI);

	protected Vector3D origin = new Vector3D(0, 0, 0);
	protected Vector3D xAxis = new Vector3D(20, 0, 0);
	protected Vector3D yAxis = new Vector3D(0, 20, 0);
	protected Vector3D zAxis = new Vector3D(0, 0, 20);

	public ArrayList<Cube> cubes = new ArrayList<Cube>();

	Renderer(){
		super("Engine");
		setSize(Main.WIDTH,Main.HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		buildMatrix();

		addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				switch (e.getKeyCode()){
				case KeyEvent.VK_W:
					if(flight){
						viewpoint.y += Math.sin(viewangle.y) * movement;
						viewpoint.x += Math.cos(viewangle.x + (Math.PI / 2.0)) * (Math.cos(viewangle.y) * movement);
						viewpoint.z += Math.sin(viewangle.x + (Math.PI / 2.0)) * (Math.cos(viewangle.y) * movement);
					}else{
						viewpoint.x += Math.cos(viewangle.x + (Math.PI / 2.0)) * movement;
						viewpoint.z += Math.sin(viewangle.x + (Math.PI / 2.0)) * movement;
					}
					break;
				case KeyEvent.VK_A:
					viewpoint.x += Math.cos(viewangle.x) * movement;
					viewpoint.z += Math.sin(viewangle.x) * movement;
					break;
				case KeyEvent.VK_S:
					if(flight){
						viewpoint.y -= Math.sin(viewangle.y) * movement;
						viewpoint.x += Math.cos(viewangle.x - (Math.PI / 2.0)) * (Math.cos(viewangle.y) * movement);
						viewpoint.z += Math.sin(viewangle.x - (Math.PI / 2.0)) * (Math.cos(viewangle.y) * movement);
					}else{
						viewpoint.x += Math.cos(viewangle.x - (Math.PI / 2.0)) * movement;
						viewpoint.z += Math.sin(viewangle.x - (Math.PI / 2.0)) * movement;
					}
					break;
				case KeyEvent.VK_D:
					viewpoint.x -= Math.cos(viewangle.x) * movement;
					viewpoint.z -= Math.sin(viewangle.x) * movement;
					break;
				case KeyEvent.VK_E:
					viewpoint.y += 1;
					break;
				case KeyEvent.VK_Q:
					viewpoint.y -= 1;
					break;
				case KeyEvent.VK_LEFT:
					viewangle.x -= 0.1;
					viewangle.x %= 2 * Math.PI;
					break;
				case KeyEvent.VK_RIGHT:
					viewangle.x += 0.1;
					viewangle.x %= 2 * Math.PI;
					break;
				case KeyEvent.VK_UP:
					if(viewangle.y < (Math.PI / 2)){
						viewangle.y += 0.1;
					}
					break;
				case KeyEvent.VK_DOWN:
					if(viewangle.y > -(Math.PI / 2)){
						viewangle.y -= 0.1;
					}
					break;
				case KeyEvent.VK_R:
					viewpoint.x = 0.0;
					viewpoint.y = 0.0;
					viewpoint.z = 0.0;
					viewangle.x=0.0;
					viewangle.y=0.0;
					viewangle.z= Math.PI;
					break;
				case KeyEvent.VK_Z:
					addCube((int)viewpoint.x, (int)viewpoint.y, (int)viewpoint.z, 10);
				}
				repaint();
			}

			public void keyReleased(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}

		});
		repaint();
	}
	protected static void buildMatrix(){

		//http://stackoverflow.com/questions/18404890/how-to-build-perspective-projection-matrix-no-api

		double frustumDepth =  (far - near);
		double oneOverDepth = 1 / frustumDepth;

		matrix[5] = 1 / Math.tan(0.5f * Main.FOV);
		matrix[0] = 1 * matrix[5] / aspectRatio;
		matrix[10] = far * oneOverDepth;
		matrix[14] = (-far * near) * oneOverDepth;
		matrix[11] = 1;
		matrix[15] = 0; 

		return;
	}

	public static Vector3D toPerspective(Vector3D point){
		Vector3D vect = new Vector3D();

		//Camera Angle
		double ax = viewangle.x;
		double ay = viewangle.y;
		double az = viewangle.z;

		//Point as if viewpoint was origin
		double ix = point.x - viewpoint.x;
		double iy = point.y - viewpoint.y;
		double iz = point.z - viewpoint.z;
		double iw = point.w - viewpoint.w;

		double x1=iz*Math.sin(ax)+ix*Math.cos(ax);
		double y1=iy;
		double z1=iz*Math.cos(ax)-ix*Math.sin(ax);

		double x11=x1;
		double y11=y1*Math.cos(ay)-z1*Math.sin(ay);
		double z11=y1*Math.sin(ay)+z1*Math.cos(ay);

		double x111=y11*Math.sin(az)+x11*Math.cos(az);
		double y111=y11*Math.cos(az)-x11*Math.sin(az);
		double z111=z11;

		ix = x111;
		iy = y111;
		iz = z111;

		double ox = matrix[0] * ix + matrix[4] * iy + matrix[8] * iz + matrix[12] * iw;
		double oy = matrix[1] * ix + matrix[5] * iy + matrix[9] * iz + matrix[13] * iw;
		double oz = matrix[2] * ix + matrix[6] * iy + matrix[10] * iz + matrix[14] * iw;
		double ow = matrix[3] * ix + matrix[7] * iy + matrix[11] * iz + matrix[15] * iw;

		vect.setX((ox * Main.WIDTH) / (2.0 * ow) + (Main.WIDTH / 2.0));
		vect.setY((oy * Main.HEIGHT) / (2.0 * ow) + (Main.HEIGHT / 2.0));
		vect.setZ(oz);
		vect.setW(ow);

		//System.out.println("X: " + vect.x + " Y: " + vect.y + " Z: " + vect.z + " W: " + vect.w);
		//System.out.println("CameraX: " + viewpoint.x + " CameraY: " + viewpoint.y + " CameraZ: " + viewpoint.z + " CameraW: " + viewpoint.w);
		//System.out.println("AngleX: " + viewangle.x + " AngleY: " + viewangle.y);

		return vect;
	}
	@Override
	public void paint(Graphics g){
		g.clearRect(0,0,Main.WIDTH,Main.HEIGHT);

		g.setColor(new Color(255,0,0)); //R
		drawLine(g, origin, xAxis);
		Vector3D X = toPerspective(xAxis);
		if(X.z >= near) g.drawString("X", (int) X.x, (int) X.y);

		g.setColor(new Color(0,255,0)); //G
		drawLine(g, origin, yAxis);
		Vector3D Y = toPerspective(yAxis);
		if(Y.z >= near) g.drawString("Y", (int) Y.x, (int) Y.y);

		g.setColor(new Color(0,0,255)); //B
		drawLine(g, origin, zAxis);
		Vector3D Z = toPerspective(zAxis);
		if(Z.z > near) g.drawString("Z", (int) Z.x, (int) Z.y);

		g.setColor(new Color(0,0,0));
		for(int x = 0; x < cubes.size(); x++){
			cubes.get(x).draw(g);
		}

		return;
	}
	public void addCube(int x, int y, int z, int len){
		cubes.add(new Cube(x, y, z, len));
		return;
	}
	public static void drawLine(Graphics g, Vector3D pointA, Vector3D pointB){
		Vector3D pointAProjected = toPerspective(pointA);// = new Vector3D();
		Vector3D pointBProjected = toPerspective(pointB);// = new Vector3D();
		if(pointAProjected.z >= near && pointBProjected.z >= near){
			g.drawLine((int)pointAProjected.x, (int)pointAProjected.y, (int)pointBProjected.x, (int)pointBProjected.y);
		}else if(pointAProjected.z >= near && pointBProjected.z < near){
			//This is broken
			double n = (pointAProjected.w - near) / (pointAProjected.w - pointBProjected.w);
			double xc = (n * pointAProjected.x) + ((1-n) * pointBProjected.x);
			double yc = (n * pointAProjected.y) + ((1-n) * pointBProjected.y);
			double zc = (n * pointAProjected.z) + ((1-n) * pointBProjected.z);
			pointBProjected = (new Vector3D(xc, yc, zc));
			//System.out.println("X: " + pointBProjected.x + " Y: " + pointBProjected.y + " Z: " + pointBProjected.z + " W: " + pointBProjected.w);
			g.drawLine((int)pointAProjected.x, (int)pointAProjected.y, (int)pointBProjected.x, (int)pointBProjected.y);
		}else if(pointAProjected.z < near && pointBProjected.z >= near){
			//This is broken
			double n = (pointBProjected.w - near) / (pointBProjected.w - pointAProjected.w);
			double xc = (n * pointBProjected.x) + ((1-n) * pointAProjected.x);
			double yc = (n * pointBProjected.y) + ((1-n) * pointAProjected.y);
			double zc = (n * pointBProjected.z) + ((1-n) * pointAProjected.z);
			pointAProjected = (new Vector3D(xc, yc , zc));
			g.drawLine((int)pointAProjected.x, (int)pointAProjected.y, (int)pointBProjected.x, (int)pointBProjected.y);
		}
		return;
	}
}