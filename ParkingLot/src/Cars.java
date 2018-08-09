public class Cars {

	final private int size;
	final private String license;
	
	public Cars(int size, String plate) {	
		this.size = size;
		this.license = plate;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getLicense() {
		 return this.license.hashCode();
	}	
}
