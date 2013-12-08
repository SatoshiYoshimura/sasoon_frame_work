/**
 *
 */
package MyArrayList;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author OWNER
 * String型のみを扱うアレイリスト
 */
public class StringArrayList implements Iterable<String> {

	private ArrayList<String> list = new ArrayList<String>();

	/**
	 *どうでもコンスト
	 */
	public StringArrayList() {

	}

	public boolean add(String s) {
	    return list.add(s);
	  }
	  public String get(int num) {
	    return (String)list.get(num);
	  }
	  public int size() {
	    return list.size();
	  }

	public Iterator<String> iterator() {
		return null;
	}

}
