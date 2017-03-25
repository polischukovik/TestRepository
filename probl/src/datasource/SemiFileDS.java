package datasource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import geometry.Point;
import geometry.Segment;
import logginig.Logger;

public class SemiFileDS implements DataSource{
	private static Logger logger = Logger.getLogger(SemiFileDS.class);
	
	private static String separator = ",";
	
	private List<Point> formPoints;
	private Segment base;
	private int devidor;	
	
	private File path;
	
	public SemiFileDS(File path) throws IOException {
		formPoints = new ArrayList<>();
		base = null;
		devidor = 0;
		formPoints = readFile(path);
	}	
	
	private static List<Point> readFile(File path) throws IOException {
		List<Point> formPoints = new ArrayList<>();
		try(Scanner sc = new Scanner(path)){			
			String line = "-1";
			
			logger .info(String.format("\nGetting form points"));
			while(sc.hasNextLine()){
				line = sc.nextLine();
				if("".equals(line)) continue;
				logger.info(String.format("Reading line: %s", line));
				String[] pair = line.split(separator);
				Point p = new Point(Double.parseDouble(pair[0]), Double.parseDouble(pair[1]));
				logger.info(String.format("  Adding point to list: %s", p));
				formPoints.add(p);
			}
			
		} catch (IOException e) {
			System.err.println(String.format("Error occured: %s", e.getMessage()));
			throw e; 
		}catch (NoSuchElementException | NumberFormatException e) {
			System.err.println(String.format("Source file is not correctly formated: %s", e.getMessage()));
			throw e; 
		}
		logger.info("\n");
		logger.info("");
		logger.info("#######################################################");
		return formPoints;
	}
	
	public boolean isValid(){
		return formPoints.size() > 2 
				&& devidor > 0 
				&& base != null 
				&& formPoints.contains(base.getA())
				&& formPoints.contains(base.getB())
				&& (Math.abs(formPoints.indexOf(base.getA()) - formPoints.indexOf(base.getB())) == 1 
					|| formPoints.indexOf(base.getA()) == 0 && formPoints.indexOf(base.getB()) == formPoints.size() - 1);
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

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "FileDS [formPoints=" + formPoints + ", base=" + base + ", devidor=" + devidor + "]";
	}
}
