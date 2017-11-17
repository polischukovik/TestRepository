package hw.lesson13.task2;

import java.util.ArrayList;

import hw.lesson10.task1.Employee;

public class Main {

	public static void main(String[] args){
		ArrayList<Integer> list = new ArrayList<>();
		list.add(5);
		list.add(3);
		list.add(7);
		list.add(1);
		list.add(8);
		sort(list);
		System.out.println(list);
	}
	
	public static void sort(ArrayList<Integer> list){
		int n = list.size();
		boolean swapped = false;

		do{
			swapped = false;
			for(int i = 1; i < n; i++){
	            if(list.get(i-1) > list.get(i)){
	            	int tmp = list.get(i-1);
	            	list.set(i-1, list.get(i));
	            	list.set(i, tmp);
	            	
	            	swapped = true;
	            }
	        }
		}while(swapped);
	}
}
