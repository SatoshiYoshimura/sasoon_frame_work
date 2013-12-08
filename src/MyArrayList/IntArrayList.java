/**
 *
 */
package MyArrayList;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author OWNER
 *int型専用のArrayList
 */
public class IntArrayList implements Iterable<Integer> {

	/**
	 *どうでもコンスト
	 */
	public IntArrayList() {

	}

	private ArrayList<Integer> list = new ArrayList<Integer>();

	public boolean add(int i) {
	    return list.add(i);
	  }
	  public int get(int num) {
	    return (int)list.get(num);
	  }
	  public int size() {
	    return list.size();
	  }
	public Iterator<Integer> iterator() {
		return null;
	}

}
