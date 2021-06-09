import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class MusicalInstrument implements InstrumentFunc {
	private Number price;
	private String brand;

	public MusicalInstrument(String brand, Number price) {
		setBrand(brand);
		setPrice(price);
	}

	public MusicalInstrument(Scanner scanner) {
		Number price = 0;
		String brand;

		try {
			if (scanner.hasNextInt()) {
				price = scanner.nextInt();
			} else if (scanner.hasNextDouble()) {
				price = scanner.nextDouble();
			} else
				throw new InputMismatchException();
		} catch (InputMismatchException ex) {
			throw new InputMismatchException("Price not found!");
		}
		setPrice(price);
		scanner.nextLine();
		brand = scanner.nextLine();
		setBrand(brand);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Number getPrice() {
		return price;
	}

	public void setPrice(Number price) {
		if (price instanceof Double) {
			if ((double) price > 0) {
				this.price = price;
			}
		} else if (price instanceof Integer) {
			if ((int) price > 0) {
				this.price = price;
			}
		} else
			throw new InputMismatchException("Price must be a positive number!");

	}

	protected boolean isValidType(String[] typeArr, String material) {
		for (int i = 0; i < typeArr.length; i++) {
			if (material.equals(typeArr[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(MusicalInstrument o) {
		int compare = this.getBrand().compareToIgnoreCase(o.getBrand());
		if (compare == 0) {
			double d = this.getPrice().doubleValue() - o.getPrice().doubleValue();
			if (d == 0)
				compare = 0;
			else if (d > 0)
				compare = 1;
			else
				compare = -1;
		}
		return compare;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof MusicalInstrument))
			return false;
		MusicalInstrument otherInstrument = (MusicalInstrument) o;
		return getPrice().doubleValue() == otherInstrument.getPrice().doubleValue()
				&& getBrand().equals(otherInstrument.getBrand());
	}

	@Override
	public String toString() {
		String str;
		if (this.price instanceof Integer) {
			str = String.format("%-8s %-9s| Price: %-7d ,", getBrand(), getClass().getCanonicalName(), getPrice());
		} else
			str = String.format("%-8s %-9s| Price: %-8.2f,", getBrand(), getClass().getCanonicalName(), getPrice());
		return str;
	}
}