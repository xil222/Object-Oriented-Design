import java.util.Calendar;

public class Space {
	final private int spotLocation;
	final private int carSize;//carSize 0-small, 1-medium, 2-large
	final private int level;//assume levels are 1,2,3
	private boolean occupied;
	final private Calendar enterTime;
		
	public Space(int spot, int size, int level, boolean occupied) {
		this.spotLocation = spot;
		this.carSize = size;//this term is the space for improvement
		this.level = level;
		this.occupied = occupied;
		this.enterTime = Calendar.getInstance();
	}
	
	public int getSize() {
		return this.carSize;
	}
	
	public int getLocation() {
		return this.spotLocation;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public boolean getCoverStatus() { //get status
		return this.occupied;
	}
	
	public boolean setCoverStatus() { //reverse status
		return this.occupied  = !this.occupied;
	}
	
	public void setEnterTime() {
		enterTime.set(Calendar.HOUR_OF_DAY, enterTime.get(Calendar.HOUR_OF_DAY));
		enterTime.set(Calendar.MINUTE, enterTime.get(Calendar.MINUTE));
		enterTime.set(Calendar.SECOND, enterTime.get(Calendar.SECOND));
	}
	
	public Calendar getEnterTime() {
		return this.enterTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Space)) {
			return false;
		}
		Space temp = (Space) obj;
		if (this.spotLocation == temp.spotLocation && this.carSize == temp.carSize && this.level == temp.level) {
			return true;
		}
		return false;
	}
	
	//use prime number 101
	@Override
	public int hashCode() {
		return spotLocation * 101 + carSize * 31 + level;
	}
}
