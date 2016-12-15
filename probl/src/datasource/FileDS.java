package datasource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import geometry.*;

public class FileDS {
	private static String separator = ",";
	
	private List<Point> formPoints;
	private Segment base;
	private int devidor;	
	
	public FileDS(String path) throws IOException {
		formPoints = new ArrayList<>();
		base = null;
		devidor = 1;
		readFile(path);
	}	
	
	private void readFile(String path) throws IOException {
		try(Scanner sc = new Scanner(new File(path))){			
			String line = "-1";
			
			System.out.println(String.format("\nGetting form points"));
			while(true){
				line = sc.nextLine();
				if("".equals(line)) break;
				System.out.println(String.format("Reading line: %s", line));
				String[] pair = line.split(separator);
				Point p = new Point(Double.parseDouble(pair[0]), Double.parseDouble(pair[1]));
				System.out.println(String.format("  Adding point to list: %s", p));
				formPoints.add(p);
			}

			System.out.println(String.format("\nGetting base segment"));		
			
			line = sc.nextLine();
			System.out.println(String.format("Reading line: %s", line));
			Point a = new Point(Double.parseDouble(line.split(separator)[0]),Double.parseDouble(line.split(separator)[1]));			
			System.out.println(String.format("Setting point A: %s", a));
			
			line = sc.nextLine();
			System.out.println(String.format("Reading line: %s", line));
			Point b = new Point(Double.parseDouble(line.split(separator)[0]),Double.parseDouble(line.split(separator)[1]));
			System.out.println(String.format("Setting point B: %s",b));
			
			base = new Segment(a, b);
			System.out.println(String.format("Base segment is: %s",base));
			
			System.out.println(String.format("\nGetting devidor"));
			
			if(!sc.nextLine().equals("")) throw new NoSuchElementException();
			line = sc.nextLine();
			devidor = Integer.parseInt(line);					
			System.out.println(String.format("Devidor is: %d", devidor));
			
		} catch (IOException e) {
			System.err.println(String.format("Error occured: %s", e.getMessage()));
			throw e; 
		}catch (NoSuchElementException | NumberFormatException e) {
			System.err.println(String.format("Source file is not correctly formated: %s", e.getMessage()));
			throw e; 
		}
		System.out.println();
		System.out.println(this);
		System.out.println("#######################################################");
	}

	
	public static String getSeparator() {
		return separator;
	}

	public static void setSeparator(String separator) {
		FileDS.separator = separator;
	}

	public List<Point> getFormPoints() {
		return formPoints;
	}

	public void setFormPoints(List<Point> formPoints) {
		this.formPoints = formPoints;
	}

	public Segment getBase() {
		return base;
	}

	public void setBase(Segment base) {
		this.base = base;
	}

	public int getDevidor() {
		return devidor;
	}

	public void setDevidor(int devidor) {
		this.devidor = devidor;
	}

	@Override
	public String toString() {
		return "FileDS [formPoints=" + formPoints + ", base=" + base + ", devidor=" + devidor + "]";
	}
}
