package orderCock_Server_DTO;

import java.util.Date;

public class History {
	private String where;
	private int price;
	private Date date;
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "History [where=" + where + ", price=" + price + ", date=" + date + "]";
	}
	
	
	
	
}
