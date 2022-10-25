package citizen;

import java.io.Serializable;
import java.util.Vector;

public class MySet<E> implements Serializable {

	private Vector<E> data;

	public MySet() {
		this.data = new Vector<E>();
	}

	// adding person to database only if he is not inside of it
	public boolean add(E element) throws MisMatchObjectException {
		if (element instanceof Citizen && data.contains(element) == false) {
			data.add(element);
			return true;
		} else {
			if (data.contains(element)) {
				return false;
			} else {
				throw new MisMatchObjectException();
			}
		}
	}

	//getting specific element from the Set
	public E get(int index) {
		return data.get(index);
	}

	public Vector<E> getData() {
		return data;
	}

	@Override
	public String toString() {
		StringBuffer strB = new StringBuffer();
		System.out.println("the data of the citizens is:");
		for (int i = 0; i < data.size(); i++) {
			strB.append(data.get(i).toString() + "\n");
			strB.append("\n");
		}
		return strB.toString();
	}

}
