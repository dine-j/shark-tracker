package sharkitter.view.map;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores map information (which pixle location points are at), 
 * and does calculations base on longitude and latitude
 * @author Maks Gajowniczek
 *
 */
public class EarthMapModel {
	private List<Point> points;
	private int scaleDown;
	private int originalHeight;
	private int originalWidth;
	private int borderPixles;
	
	public EarthMapModel(int originalWidth, int originalHeight, int scaleDown, int borderPixles)
	{
		points = new ArrayList<>();
		
		this.scaleDown = scaleDown;
		this.originalHeight = originalHeight;
		this.originalWidth = originalWidth;
		this.borderPixles = borderPixles;
	}
	
	public List<Point> getPoints()
	{
		return points;
	}
	
	public void addMapCoords(List<double[]> longLatPoints) throws IllegalArgumentException
	{
		try{
			for (double[] longLat: longLatPoints)
			{
				addMapCoord(longLat[0], longLat[1]);
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			System.out.println("Some coordinates in double[] are less then size 2!");
		}
	}
	
	public Point addMapCoord(double longitude, double latitude)
	{
		Point p = convertToMapCoord(longitude, -latitude); //PLEASE CHANGE , JUST DONE A HACK FOR NOW with minus
		points.add(p);
		return p;
	}
	
	// preconditions: -180< longitude <= 180 , -180< latitude <= 180
	private Point convertToLargeMapCoord(double longitude, double latitude)
	{
		int mapWidth = originalWidth - 2 * borderPixles;
		int mapHeight = originalHeight - 2 * borderPixles;
		
		double ratioX = (longitude + 180 ) / 360.0; 
		double ratioY = (latitude + 90) / 180.0;
		
		int x, y;
		x = (int)Math.round((borderPixles + ratioX * mapWidth));
		y = (int)Math.round((borderPixles + ratioY * mapHeight));
		return new Point(x,y);
	}
	
	// preconditions: -180< longitude <= 180 , -180< latitude <= 180
	public Point convertToMapCoord(double longitude, double latitude)
	{
		Point p = convertToLargeMapCoord(longitude, latitude);
		p.x /= scaleDown;
		p.y /= scaleDown;
		return p;
	}
	
	// // preconditions: 0 =< x <= orginalWidth/scaleDown , 0 =< y <= originalHeight/scaleDown
	public double[] getLongLatCoord(int x, int y)
	{
		// working out from original map
		int pixX = x * scaleDown - borderPixles;
		int pixY = y * scaleDown - borderPixles;
		double mapWidth = originalWidth - 2 * borderPixles;
		double mapHeight = originalHeight - 2 * borderPixles;
		double longitude = (pixX / mapWidth * 360)- 180;
		double latitude = -(pixY / mapHeight * 180) + 90;
		
		return new double[]{longitude, latitude};
	}
}