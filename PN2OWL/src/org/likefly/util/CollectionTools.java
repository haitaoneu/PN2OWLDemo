package org.likefly.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class CollectionTools {	
	public static boolean containsOnlyInstancesOf(Collection collection, Class clazz) {
		for (Object object : collection) {
			if (object.getClass() != clazz) {
				return false;
			}
		}
		return true;
	}
	
	public static <E> E getFirstElement(Collection<E> elements) {
		E firstElement = null;
		for (E element : elements) {
			firstElement = element;
			break;
		}
		return firstElement;
	}
	public static <E> E getLastElement(Collection<E> elements) {
		E lastElement = null;
		for (E element : elements) {
			lastElement = element;
		}
		return lastElement;
	}
	
	private final static Random random = new Random();
	public static <E> E getRandomElement(List<E> elements) {
		int randomIndex = random.nextInt(elements.size());
		return elements.get(randomIndex);
	}

	@SuppressWarnings(value="unchecked")
	public static <E> Set<E> getFilteredByClass(Collection<?> elements, Class<E> clazz) {
		Set<E> result = new HashSet<E>();
		for (Object element : elements) {
			if (element != null) {
                if (element.getClass() == clazz) {
                    result.add((E)element);
                }
            }
		}
		return result;
	}
}
