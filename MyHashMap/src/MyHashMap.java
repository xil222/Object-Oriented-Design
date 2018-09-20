import java.util.Arrays;
import java.util.concurrent.*;

public class MyHashMap<K, V> {
	
	public static class Entry<K, V> { //see whats going on without <K, V>
		final K key;
		V value;
		
		Entry<K, V> next;
		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public V getValue(){
			return value;
		}
		
		public K getKey(){
			return key;	
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	public static final float defaultLoadFactor = 0.75f;
	public static final int defaultCapacity = 16;
	
	private Entry<K, V>[] myArray;
	private int size;
	private float loadFactor;
	
	public MyHashMap() {
		this(defaultCapacity, defaultLoadFactor);
	}
	
	public MyHashMap(int capacity, float loadFactor) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity should be positive");
		}
		this.myArray = (Entry<K, V>[])(new Entry[capacity]);
		this.loadFactor = loadFactor;
		this.size = 0;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.size == 0? true: false;
	}
	
	public void clear() {
		Arrays.fill(myArray, null);
	}
	
	public int hash(K key) {
		if (key == null) {
			return 0; //
		}
		return key.hashCode() & 0x7FFFFFFF; 
	}
	
	public int getIndex(int hashValue) {
		return hashValue % myArray.length;
	}
	
	public boolean equalsKey(K k1, K k2) {
		//in hashMap we allow null key exist in the 0th place 
		return k1 == k2 || (k1 != null && k1.equals(k2));
	}
	
	public boolean equalsValue(V v1, V v2) { 
		return v1 == v2 || (v1 != null && v1.equals(v2));
	}
	
	public synchronized V get(K key) {
		//if key is null return null at most, what need is only a value
		int index = getIndex(hash(key));
		Entry<K,V> currEntry = myArray[index];
		
		while (currEntry != null) {
			if (equalsKey(currEntry.getKey(),key)) {//key is null, then null.equals --> NPE
				return currEntry.getValue();//?
			}
			currEntry = currEntry.next;
		}
		return null;
	}
	
	public synchronized boolean containsKey(K key){
		if (this.isEmpty()) {
			return false;
		}
		
		int index = getIndex(hash(key));
		Entry<K,V> currEntry = myArray[index];
		while (currEntry != null) {
			if (equalsKey(currEntry.getKey(),key)) {
				return true;
			}
			currEntry = currEntry.next;
		}
		return false;
	}
	
	public boolean containsValue(V value){
		if (this.isEmpty()) {
			return false;
		}
		
		for (Entry<K, V> currEntry: myArray) {
			while (currEntry != null) {
				if (equalsValue(currEntry.getValue(),value)){
					return true;
				} else {
					currEntry = currEntry.next;
				}
			}
		}
		return false;
	}
	
	//return the original value of specific key
	public synchronized V put(K key, V value){
		
		int index = getIndex(hash(key));
		Entry<K, V> currEntry = myArray[index];
		Entry<K, V> head = currEntry;
		
		while (currEntry != null) {
			if (equalsKey(currEntry.getKey(),key)) {
				V currValue = currEntry.getValue();
				currEntry.setValue(value);
				return currValue;
			}
			currEntry = currEntry.next;
		}
		
		//if getting out of this loop, meaning we've not found the node we need
		Entry<K, V> newhead = new Entry<K, V>(key, value);
		newhead.next = head;
		myArray[index] = newhead;
		this.size++;
		if (needRehashing()){
			rehashing();
		}
		return null;
	}
	
	public boolean needRehashing() {
		float ratio = (this.size + 0.0f) / myArray.length;
		return ratio >= loadFactor;
	}
	
	public synchronized void rehashing() {
		//System.out.println(myArray.length);
		int newCapacity = myArray.length * 2;
		Entry<K, V>[] tempArray = myArray;
		Entry<K, V>[] myArray = (Entry<K, V>[])(new Entry[newCapacity]);
		
		for (Entry<K, V> entry: tempArray) {
			while (entry != null) {
				Entry<K, V> nextEntry = entry.next;
				int newIndex = getIndex(hash(entry.getKey()));
				Entry<K, V> curHead = myArray[newIndex];
				entry.next = curHead;
				myArray[newIndex] = entry;
				entry = nextEntry;
			}
		}
	}
	
	public synchronized V remove(K key) {
		if (isEmpty()) {
			return null;
		}
		
		int index = getIndex(hash(key));
		//1 search for the key
		//delete
		Entry<K, V> currEntry = myArray[index];
		Entry<K, V> prevEntry = null;
		
		while (currEntry != null) {
			if (equalsKey(key,currEntry.getKey())) {
				V currValue = currEntry.getValue();
				if (prevEntry == null) {
					myArray[index] = currEntry.next;
				} else {
					prevEntry.next = currEntry.next;
				}
				size--;
				return currValue;
			}
			prevEntry = currEntry;
			currEntry = currEntry.next;
		}
		return null;
	}
	
	public static void main(String[] args) {
		MyHashMap<Character,Integer> firstMap = new MyHashMap<Character,Integer>(8, 0.75f);
		System.out.println(firstMap.size());
		firstMap.put('a',2);
		firstMap.put('a',3);
		firstMap.put('a',4);
		firstMap.put('a',5);
		firstMap.put('a',6);
		firstMap.put('a',7);
		System.out.println(firstMap.size());
		
		firstMap.put('b',3);
		firstMap.put('c',4);
		firstMap.put('d',5);
		firstMap.put('e',6);
		firstMap.put('f',7);
		System.out.println(firstMap.size());		
	}
	
	
	
	
	
	
}
