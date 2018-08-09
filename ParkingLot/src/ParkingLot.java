import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*goals: 1. Implement a parking-lot system which can record number of cars, spaces 
 		    in the parking lot, and get space info according to license number
		
		 2. calculate fee for cars according to time elapsed 

		 3. make an intelligent system recommending the closest spot(level) for cars 
*/

public class ParkingLot{

	//final int levels;
	final int smallCarCapacity; //0 to represent small size
	final int midCarCapacity; //1 to represent mid size
	final int largeCarCapacity; //2 to represent large size
	
	Map <Integer,Space> hashMap; 
	//Car, Integer which car represents car's info, 
	//Space represents spot location
	//Integer represents 
	
	Set<Space> smallCoveredSpace;
	Set<Space> smallEmptySpace;
	Set<Space> mediumCoveredSpace;
	Set<Space> mediumEmptySpace;
	Set<Space> largeCoveredSpace;
	Set<Space> largeEmptySpace;
	
	public ParkingLot(int smallCarCapacity, int midCarCapacity, int largeCarCapacity) {
		//assume we have same capacity of same type of cars on each floor
		this.smallCarCapacity = smallCarCapacity; //capacity of cars on each floor
		this.midCarCapacity = midCarCapacity;
		this.largeCarCapacity = largeCarCapacity;
		this.hashMap = new HashMap<>();	
		smallCoveredSpace = new HashSet<>();
		smallEmptySpace = new HashSet<>();
		mediumCoveredSpace = new HashSet<>();
		mediumEmptySpace = new HashSet<>();
		largeCoveredSpace = new HashSet<>();
		largeEmptySpace = new HashSet<>();
		setEmptySpace();
	}
	
	private void setEmptySpace() {
		for (int i = 0; i < smallCarCapacity; i++) {
			smallEmptySpace.add(new Space(i,0,1)); 
			smallEmptySpace.add(new Space(i,0,2));
			smallEmptySpace.add(new Space(i,0,3));
		}
		
		for (int i = 0; i < midCarCapacity; i++) {
			mediumEmptySpace.add(new Space(i,1,1));
			mediumEmptySpace.add(new Space(i,1,2));
			mediumEmptySpace.add(new Space(i,1,3));
		}
		
		for (int i = 0; i < largeCarCapacity; i++) {
			largeEmptySpace.add(new Space(i,2,1));
			largeEmptySpace.add(new Space(i,2,2));
			largeEmptySpace.add(new Space(i,2,3));
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
			Iterator<Space> iterator = smallEmptySpace.iterator();
			Space newSpace = iterator.next();
			hashMap.put(car.getLicense(),newSpace);
			smallEmptySpace.remove(newSpace); 
			smallCoveredSpace.add(newSpace);
			System.out.println("License: " + car.getLicense()+" Type: "+ newSpace.getSize() + " Floor: " + newSpace.getLevel() + " Spot: " + newSpace.getLocation());
		} else if (carSize == 1) {
			if (mediumEmptySpace.isEmpty()) {
				System.out.println("Spaces for medium cars are full");
				return false;
			}
			Iterator<Space> iterator = mediumEmptySpace.iterator();
			Space newSpace = iterator.next();
			hashMap.put(car.getLicense(),newSpace);
			mediumEmptySpace.remove(newSpace); 
			mediumCoveredSpace.add(newSpace);
			System.out.println("License: " + car.getLicense()+" Type: "+ newSpace.getSize() + " Floor: " + newSpace.getLevel() + " Spot: " + newSpace.getLocation());
		} else {
			if (largeEmptySpace.isEmpty()) {
				System.out.println("Spaces for large cars are full");
				return false;
			}
			Iterator<Space> iterator = largeEmptySpace.iterator();
			Space newSpace = iterator.next();
			hashMap.put(car.getLicense(),newSpace);
			largeEmptySpace.remove(newSpace);
			largeCoveredSpace.add(newSpace);
			System.out.println("License: " + car.getLicense()+" Type: "+ newSpace.getSize() + " Floor: " + newSpace.getLevel() + " Spot: " + newSpace.getLocation());
		}
		return true;
	}
	
	//get car's space from Car's License
	
	//public Space getCarsInfo(Integer licenseNumber) {
	public Space getCarsInfo(String license) {
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
		Space carSpace = hashMap.get(vehicleHash);
		hashMap.remove(vehicleHash); //remove the key
		int carSize = carSpace.getSize();
		switch(carSize) {
			case 0: 
				smallEmptySpace.add(carSpace);
				smallCoveredSpace.remove(carSpace);
				return;
			case 1: 
				mediumEmptySpace.add(carSpace);
				mediumCoveredSpace.remove(carSpace);
				return;
			case 2: 
				largeEmptySpace.add(carSpace);
				largeCoveredSpace.remove(carSpace);
				return;
			default:
				return;
		}
	}
	
	public void showRemainingSpots() {
		System.out.println("Small Spaces Remainings: " + smallEmptySpace.size());
		System.out.println("Medium Spaces Remainings: " + mediumEmptySpace.size());
		System.out.println("Large Spaces Remainings: " + largeEmptySpace.size());	
	}
	
	public void showOccupiedSpots() {
		System.out.println("Totals Cars Parked: " + hashMap.size());
		System.out.println("Small Spaces Occupied: " + smallCoveredSpace.size());
		System.out.println("Medium Spaces Occupied: " + mediumCoveredSpace.size());
		System.out.println("Large Spaces Occupied: " + largeCoveredSpace.size());	
	}
	
	public static void main (String[] args) {
		ParkingLot newParkingLot = new ParkingLot(5,5,5);
		newParkingLot.addVehcicle(new Cars(1, "A111"));
		newParkingLot.addVehcicle(new Cars(0, "B222"));
		newParkingLot.addVehcicle(new Cars(1, "C333"));
		newParkingLot.addVehcicle(new Cars(2, "D444"));
		newParkingLot.showRemainingSpots();
		newParkingLot.getCarsInfo("D444");
		newParkingLot.getCarsInfo("D443");
		newParkingLot.removeVehicle("C333");
		newParkingLot.showOccupiedSpots();
	}

}
