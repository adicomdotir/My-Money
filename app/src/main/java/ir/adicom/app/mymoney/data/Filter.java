package ir.adicom.app.mymoney.data;

public class Filter {
	private String categoryId;
	private long sum;
	private long count;

	public Filter(long sum, long count, String categoryId) {
		this.sum = sum;
		this.count = count;
		this.categoryId = categoryId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}