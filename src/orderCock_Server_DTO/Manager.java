package orderCock_Server_DTO;

public class Manager {
	private String name;
	private String tel;
	private String market;
	private String Adress;
	private float x;
	private float y;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getAdress() {
		return Adress;
	}
	public void setAdress(String adress) {
		Adress = adress;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Manager [name=" + name + ", tel=" + tel + ", market=" + market + ", Adress=" + Adress + ", x=" + x
				+ ", y=" + y + "]";
	}
}
