package main;

import java.util.ArrayList;

public class ThreadSafeList<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3011789674328280450L;
	
	@Override
	public synchronized final T get(final int i) {
		return super.get(i);
	}
	
	@Override
	public synchronized final boolean add(final T t) {
		return super.add(t);
	}
	
	@Override
	public synchronized final T remove(final int i) {
		return super.remove(i);
	}
	
	@Override
	public synchronized final boolean remove(final Object t) {
		return super.remove(t);
	}
	
	@Override
	public synchronized final T set(final int index, final T t) {
		return super.set(index, t);
	}
}
