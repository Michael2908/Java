import java.util.Scanner;

public class Instruments {
	private String brand;
	private double price;

	public Instruments(Double price, String brand) {
		setBrand(brand);
		try {
			setPrice(price);
		} catch (Exception e) {
			System.exit(0);
		}
	}

	public Instruments(Scanner f) throws NumberFormatException, Exception {
		setPrice((f.nextDouble()));
		f.nextLine();
		setBrand(f.nextLine());
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double i) throws Exception {
		if (0 <= i) {
			this.price = i;
		} else
			throw new NumberFormatException("Price must be a positive number!");
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	private String setSpaces() {
		String s;
		if (getPrice() == 300) {
			s = "Price:  ";
		} else {
			s = "Price: ";
		}
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Instruments) {
			Instruments temp = (Instruments) obj;
			if (temp.getPrice() == this.getPrice()) {
				if (temp.getBrand().compareTo(getBrand()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String str = String.format("%1$-10s", getBrand()) + String.format("%1$-10s", getClass().getCanonicalName())
				+ "| " + setSpaces() + String.format("%4.02f", getPrice()) + ",  ";
		return str;
	}
}
