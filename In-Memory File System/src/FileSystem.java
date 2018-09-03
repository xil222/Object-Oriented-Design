import java.util.List;
import java.util.ArrayList;

public class FileSystem {

	private final Directory root;
	
	public FileSystem() {
		root = new Directory("/", null);
	}
	
	private List<Entry> resolve(String path) {
		assert path.startsWith("/");
		String[] components = path.substring(1).split("/");
		List<Entry> entries = new ArrayList<Entry>(components.length + 1);
		entries.add(root);
		Entry entry = root;
		for (String component: components) {
			if (entry == null || !(entry instanceof Directory)){
				throw new IllegalArgumentException("invalid path: " + path);
			}
			
			if (!component.isEmpty()){
				entry = ((Directory) entry).getChild(component);
				entries.add(entry);
			}			
		}
		return entries;
	}
	
	public void mkdir(String path) {
		List<Entry> entries = resolve(path);
		if (entries.get(entries.size()-1) != null) {
			throw new IllegalArgumentException("Directory alreday exists: "+ path);
		}
		
		String[] components = path.split("/");
		final String dirName = components[components.length - 1];
		final Directory parent = (Directory)entries.get(entries.size() - 2);
		Directory newDir = new Directory(dirName, parent);
		parent.addEntry(newDir);
	}
	
	public void createFile(String path) {
		assert !path.endsWith("/");
		List<Entry> entries = resolve(path);
		if (entries.get(entries.size() - 1) != null) {
			throw new IllegalArgumentException("File Already exists: " + path);
		}
		
		final String fileName = path.substring(path.lastIndexOf("/") + 1);
		final Directory parent = (Directory)entries.get(entries.size() - 2);
		File file = new File(fileName, parent, 0);
		parent.addEntry(file);
	}
	
	public void delete(String path) {
		List<Entry> entries = resolve(path);
		if (entries.get(entries.size()-1) != null) {
			throw new IllegalArgumentException("Directory alreday exists: "+ path);
		}
		
		final Directory parent = (Directory)entries.get(entries.size() - 2);
		final Entry lastEntry = entries.get(entries.size() - 1);
		parent.deleteEntry(lastEntry);
	}
	
	public List<Entry> list(String path) {
		List<Entry> entries = resolve(path);//put all the subfiles and subfolders into the entry
		Entry entry = entries.get(entries.size() - 1);
		List<Entry> result = new ArrayList<>();
		if (!path.endsWith("/")) {
			List<Entry> contents = ((Directory) entry).getContents();
			for (Entry e: contents) {
				result.add(e);
			}
		} 
		return result;
	}
	
	public int count() {
		return root.numberOfFiles();
	}
	

}
