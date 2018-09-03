import java.util.List;
import java.util.ArrayList;

public class Directory extends Entry{

	protected List<Entry> contents;
	
	public Directory(String n, Directory p) {
		super(n,p);
		contents = new ArrayList<Entry>();
	}
	
	protected List<Entry> getContents() {
		return contents;
	}
	
	//entry here might be directory or file, use the size defined there
	public int size() {
		int size = 0;
		for (Entry e: contents) {
			size += e.size();
		}
		return size;
	}
	
	public int numberOfFiles() {
		int count = 0;
		for (Entry e: contents) {
			if (e instanceof Directory) {
				count++;
				Directory d = (Directory) e;
				count += d.numberOfFiles();
			} else {
				count++;
			}
		}
		return count;		
	}
	
	public boolean deleteEntry(Entry entry) {
		return contents.remove(entry);
	}
	
	public void addEntry(Entry entry) {
		contents.add(entry);
	}
	
	public Entry getChild(String s){
		for (Entry c: contents) {
			if (c.name.equals(s)){
				return c;
			}
		}
		return null;
	}
	
	

}
	