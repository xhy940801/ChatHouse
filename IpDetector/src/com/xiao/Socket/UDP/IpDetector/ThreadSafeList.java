package com.xiao.Socket.UDP.IpDetector;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeList<T>
{
	public static final int DefaultInitialCapacity = 5;
	private int initCapacity = ThreadSafeList.DefaultInitialCapacity;
	private Object[] data;
	private int length, capacity;
	
	private Lock lock = new ReentrantLock();
	
	public ThreadSafeList()
	{
		this.data = new Object[ThreadSafeList.DefaultInitialCapacity];
		this.length = 0;
		this.capacity = ThreadSafeList.DefaultInitialCapacity;
	}
	
	public ThreadSafeList(int initCapacity)
	{
		this.data = new Object[initCapacity];
		this.length = 0;
		this.capacity = initCapacity;
		this.initCapacity = initCapacity;
	}
	
	public void addElement(Object element)
	{
		this.lock.lock();
		this.ensureEnoughCapacity(length + 1);
		this.data[this.length++] = element;
		this.lock.unlock();
	}
	
	public void addElementNoRepeat(Object element)
	{
		for(int i=0;i<length;++i)
		{
			if(data[i].equals(element))
				return;
		}
		this.addElement(element);
	}
	
	public Object[] getAllAndFlush()
	{
		if(length == 0)
			return null;
		this.lock.lock();
		Object[] re = new Object[this.length];
		System.arraycopy(this.data, 0, re, 0, this.length);
		this.data = new Object[this.initCapacity];
		this.length = 0;
		this.capacity = this.data.length;
		this.lock.unlock();
		return re;
	}
	
	public T[] getAllAndFlush(Class<? extends T[]> c)
	{
		if(length == 0)
			return null;
		this.lock.lock();
		T[] re = (T[])Arrays.copyOf(this.data, length, c);
		
		this.data = new Object[this.initCapacity];
		this.length = 0;
		this.capacity = this.data.length;
		
		this.lock.unlock();
		return re;
	}
	
	private void ensureEnoughCapacity(int minCapacity)
	{
		if(this.capacity >= minCapacity)
			return;
		else
		{
			this.capacity += (this.capacity >> 2);
			if(this.capacity < minCapacity)
				this.capacity = minCapacity;
			Object[] newArray = new Object[this.capacity];
			System.arraycopy(this.data, 0, newArray, 0, this.data.length);
			this.data = newArray;
		}
	}
}
