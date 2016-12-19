package datasource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import geometry.*;
import probl.App;

public class SemiFileDS implements DataSource{
	private static String separator = ",";
	
	private List<Point> formPoints;
	private Segment base;
	private int devidor;	
	
	public SemiFileDS(File path) throws IOException {
		formPoints = new ArrayList<>();
		base = null;
		devidor = 1;
		readFile(path);
	}	
	
	private void readFile(File path) throws IOException {
		try(Scanner sc = new Scanner(path)){			
			String line = "-1";
			
			App.log.info(this.getClass(), String.format("\nGetting form points"));
			while(sc.hasNextLine()){
				line = sc.nextLine();
				if("".equals(line)) continue;
				App.log.info(this.getClass(), String.format("Reading line: %s", line));
				String[] pair = line.split(separator);
				Point p = new Point(Double.parseDouble(pair[0]), Double.parseDouble(pair[1]));
				App.log.info(this.getClass(), String.format("  Adding point to list: %s", p));
				formPoints.add(p);
			}
			
		} catch (IOException e) {
			System.err.println(String.format("Error occured: %s", e.getMessage()));
			throw e; 
		}catch (NoSuchElementException | NumberFormatException e) {
			System.err.println(String.format("Source file is not correctly formated: %s", e.getMessage()));
			throw e; 
		}
		App.log.info(this.getClass(), "\n");
		App.log.info(this.getClass(), this.toString());
		App.log.info(this.getClass(), "#######################################################");
	}
	
	public boolean isValid(){
		return formPoints.size() > 2 
				&&  devidor > 0 
				&& base != null 
				&& formPoints.contains(formPoints.contains(base.getA()))
				&& formPoints.contains(base.getB())
				&& Math.abs(formPoints.indexOf(formPoints.contains(base.getA())) - formPoints.indexOf(formPoints.contains(base.getB()))) == 1;
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
