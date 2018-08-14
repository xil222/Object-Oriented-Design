import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Calendar;

/*goals: 1. Implement a parking-lot system which can record number of cars, spaces 
 		    in the parking lot, and get space info according to license number
		
		 2. calculate fee for cars according to time elapsed 
		 	assume a car have to leave before 23:59 everyday

		 3. make an intelligent system recommending the closest spot(level) for cars  
*/

public class ParkingLot{

	final int smallCarCapacity; //0 to represent small size
	final int midCarCapacity; //1 to represent mid size
	final int largeCarCapacity; //2 to represent large size
	
	Map <Integer,Space> hashMap; 
	//Car, Integer which car represents car's info, 
	//Space represents spot location
	//Integer represents 
		
	Set<Space> smallEmptySpace;
	Set<Space> mediumEmptySpace;
	Set<Space> largeEmptySpace;
	PriorityQueue<Space> smallCarEmptyQueue;
	PriorityQueue<Space> mediumCarEmptyQueue;
	PriorityQueue<Space> largeCarEmptyQueue;
	
	Comparator<Space> newComparator = new Comparator<Space>() {
		@Override
		public int compare(Space a, Space b) {
			if (a.getLevel() != b.getLevel()) {
				return a.getLevel() - b.getLevel(); //maintain a minheap first based on level
			} else {
				return a.getLocation() - b.getLocation(); //2nd according to spotposition (assume smaller number closer)
			}
		}
	};
	
	public ParkingLot(int smallCarCapacity, int midCarCapacity, int largeCarCapacity) {
		//assume we have same capacity of same type of cars on each floor
		this.smallCarCapacity = smallCarCapacity; //capacity of cars on each floor
		this.midCarCapacity = midCarCapacity;
		this.largeCarCapacity = largeCarCapacity;
		this.hashMap = new HashMap<>();	
		smallEmptySpace = new HashSet<>();
		mediumEmptySpace = new HashSet<>();
		largeEmptySpace = new HashSet<>();
		smallCarEmptyQueue = new PriorityQueue<Space>(10, newComparator);
		mediumCarEmptyQueue = new PriorityQueue<Space>(10, newComparator);
		largeCarEmptyQueue = new PriorityQueue<Space>(10, newComparator);
		setEmptySpace();
	}
	
	private void setEmptySpace() {
		for (int i = 0; i < smallCarCapacity; i++) {
			smallEmptySpace.add(new Space(i,0,1,false)); 
			smallEmptySpace.add(new Space(i,0,2,false));
			smallEmptySpace.add(new Space(i,0,3,false));
			smallCarEmptyQueue.offer(new Space(i,0,1,false));
			smallCarEmptyQueue.offer(new Space(i,0,2,false));
			smallCarEmptyQueue.offer(new Space(i,0,3,false));
		}
		
		for (int i = 0; i < midCarCapacity; i++) {
			mediumEmptySpace.add(new Space(i,1,1,false));
			mediumEmptySpace.add(new Space(i,1,2,false));
			mediumEmptySpace.add(new Space(i,1,3,false));
			mediumCarEmptyQueue.offer(new Space(i,1,1,false));
			mediumCarEmptyQueue.offer(new Space(i,1,2,false));
			mediumCarEmptyQueue.offer(new Space(i,1,3,false));
		}
	
		for (int i = 0; i < largeCarCapacity; i++) {
			largeEmptySpace.add(new Space(i,2,1,false));
			largeEmptySpace.add(new Space(i,2,2,false));
			largeEmptySpace.add(new Space(i,2,3,false));
			largeCarEmptyQueue.offer(new Space(i,2,1,false));
			largeCarEmptyQueue.offer(new Space(i,2,2,false));
			largeCarEmptyQueue.offer(new Space(i,2,3,false));
		}
	}

	//remove space from EmptySpace 
	//add space into the CoveredSpace
	public boolean addVehcicle(Cars car) {
		int carSize = car.getSize();			
		if (carSize == 0) {
			if (smallEmptySpace.isEmpty()) {
				System.out.println("Spaces for small cars are full");
				return false;
			}
			//Iterator<Space> iterator = smallEmptySpace.iterator();
			//Space newSpace = iterator.next();
			Space newSpace = smallCarEmptyQueue.poll();
			//set space to be covered
			newSpace.setCoverStatus();
			//initialize start time
			newSpace.setEnterTime();
			hashMap.put(car.getLicense(),newSpace);
			smallEmptySpace.remove(newSpace); 
			System.out.println("License: " + car.getLicense()+" Type: "+ newSpace.getSize() + " Floor: " + newSpace.getLevel() + " Spot: " + newSpace.getLocation());
		} else if (carSize == 1) {
			if (mediumEmptySpace.isEmpty()) {
				System.out.println("Spaces for medium cars are full");
				return false;
			}
			Space newSpace = mediumCarEmptyQueue.poll();
			newSpace.setCoverStatus();
			newSpace.setEnterTime();
			hashMap.put(car.getLicense(),newSpace);
			mediumEmptySpace.remove(newSpace); 
			System.out.println("License: " + car.getLicense()+" Type: "+ newSpace.getSize() + " Floor: " + newSpace.getLevel() + " Spot: " + newSpace.getLocation());
		} else {
			if (largeEmptySpace.isEmpty()) {
				System.out.println("Spaces for large cars are full");
				return false;
			}
			Space newSpace = mediumCarEmptyQueue.poll();
			newSpace.setCoverStatus();
			newSpace.setEnterTime();
			hashMap.put(car.getLicense(),newSpace);
			largeEmptySpace.remove(newSpace);
			System.out.println("License: " + car.getLicense()+" Type: "+ newSpace.getSize() + " Floor: " + newSpace.getLevel() + " Spot: " + newSpace.getLocation());
		}
		return true;
	}
	
	//get car's space from Car's License	
	//public Space getCarsInfo(Integer licenseNumber) {
	public Space getSpaceInfo(String license) {
		Integer licenseNumber = license.hashCode();
		if (hashMap.containsKey(licenseNumber)) {
			Space temp = hashMap.get(licenseNumber);
			System.out.println("Type: " + temp.getSize() + " Spot: " + temp.getLocation());
			return temp;
		} else {
			System.out.println("No such car exists");
			return null;
		}
	}
	
	//remove a car from its parking spot
	public void removeVehicle(String license) {
		Integer vehicleHash = license.hashCode();
		if (!hashMap.containsKey(vehicleHash)) {
			return;
		}
		Calendar leaveTime = Calendar.getInstance();		
		Space carSpace = hashMap.get(vehicleHash);
		int cost = calculateCost(carSpace.getEnterTime(),leaveTime);
		System.out.println("cost " + cost);
		carSpace.setCoverStatus(); //set the cover condition to false
		hashMap.remove(vehicleHash); //remove the key
		int carSize = carSpace.getSize();
		switch(carSize) {
			case 0: 
				smallCarEmptyQueue.offer(carSpace);
				smallEmptySpace.add(carSpace);
				return;
			case 1: 
				mediumCarEmptyQueue.offer(carSpace);
				mediumEmptySpace.add(carSpace);
				return;
			case 2: 
				largeCarEmptyQueue.offer(carSpace);
				largeEmptySpace.add(carSpace);
				return;
			default:
				return;
		}
	}
	
	public int calculateCost(Calendar enter, Calendar leave) {
		//assume 5 dollars for first 3 hours
		//3 dollars for each next hour
		int baseCost = 5;
		Calendar result = Calendar.getInstance();
		result.set(Calendar.HOUR_OF_DAY, leave.get(Calendar.HOUR_OF_DAY) - enter.get(Calendar.HOUR_OF_DAY));
		result.set(Calendar.MINUTE, leave.get(Calendar.MINUTE) - enter.get(Calendar.MINUTE));
		result.set(Calendar.SECOND, leave.get(Calendar.SECOND) - enter.get(Calendar.SECOND));
		
		int totalSeconds = result.get(Calendar.HOUR_OF_DAY) * 3600 + result.get(Calendar.MINUTE) * 60 + result.get(Calendar.SECOND);
		if (totalSeconds <= 18000) {
			return baseCost;
		} else {
			double hours = (totalSeconds - 18000)/3600.0;
			int totalCost = baseCost + 3 * (int)Math.ceil(hours);
			return totalCost;
		}
		
	}
	
	public void showRemainingSpots() {
		System.out.println("Small Spaces Remainings: " + smallEmptySpace.size());
		System.out.println("Medium Spaces Remainings: " + mediumEmptySpace.size());
		System.out.println("Large Spaces Remainings: " + largeEmptySpace.size());	
	}
	
	public void showOccupiedSpots() {
		System.out.println("Totals Cars Parked: " + hashMap.size());
	}
	
	public static void main (String[] args) {
		ParkingLot newParkingLot = new ParkingLot(5,5,5);
		newParkingLot.addVehcicle(new Cars(1, "A111"));
		newParkingLot.addVehcicle(new Cars(0, "B222"));
		newParkingLot.addVehcicle(new Cars(1, "C333"));
		newParkingLot.addVehcicle(new Cars(2, "D444"));
		newParkingLot.showRemainingSpots();
		newParkingLot.getSpaceInfo("D444");
		newParkingLot.removeVehicle("C333");
		newParkingLot.showOccupiedSpots();
	}

}
