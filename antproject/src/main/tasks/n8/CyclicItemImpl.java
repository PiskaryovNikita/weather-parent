package main.tasks.n8;

import java.io.Serializable;

import interfaces.task8.CyclicItem;

public class CyclicItemImpl implements CyclicItem, Serializable {
	private static final long serialVersionUID = 1L;
	private transient Object temp;
	private Object value;
	private CyclicItem next;

	{
		next = this;
	}

	public CyclicItemImpl() {
	}

	public CyclicItemImpl(Object value) {
		this.value = value;
	}

	public CyclicItemImpl(Object value, Object temp) {
		this.value = value;
		this.temp = temp;
	}

	@Override
	public Object getTemp() {
		return temp;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public CyclicItem nextItem() {
		return next;
	}

	@Override
	public void setNextItem(CyclicItem next) {
		this.next = next;
	}

	@Override
	public void setTemp(Object temp) {
		this.temp = temp;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		CyclicItem cyclicItem = (CyclicItem) obj;
		return value.equals(cyclicItem.getValue());
	}

	@Override
	public String toString() {
		if (temp != null) {
			return value + "::" + temp;
		} else {
			return value + "";
		}
	}

}
