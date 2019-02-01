package main.tasks.n7.executor;

import java.math.BigInteger;

import interfaces.task7.executor.SumTask;

public class SumTaskImpl extends TaskImpl implements SumTask {
	private int count;
	private long max;
	private BigInteger result = new BigInteger("0");

	public SumTaskImpl() {
	}

	public SumTaskImpl(int count, long max) {
		this.count = count;
		this.max = max;
	}

	@Override
	public boolean execute() throws Exception {
		for (int i = 0; i < count; i++) {
			result = result.add(getRandom());
		}
		return true;
	}

	@Override
	public BigInteger getRandom() {
		Double d = Math.random() * max;
		return new BigInteger(d.intValue() + "");
	}

	@Override
	public BigInteger getResult() {
		return result;
	}

	@Override
	public void setCount(int c) {
		this.count = c;
	}

	@Override
	public void setMax(long m) {
		if (m < 1) {
			throw new IllegalArgumentException();
		}
		this.max = m;
	}

	public static void main(String[] args) {
		SumTaskImpl sumTaskImpl = new SumTaskImpl();
		sumTaskImpl.setCount(10);
		sumTaskImpl.setMax(1553756211);
		try {
			sumTaskImpl.execute();
			System.out.println(sumTaskImpl.getResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
