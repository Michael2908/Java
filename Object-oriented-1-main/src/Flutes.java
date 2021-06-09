import java.util.InputMismatchException;
import java.util.Scanner;

public class Flutes extends WoodwindInstruments {
	private String fluteType;
	private final String[] AVAILABLE_TYPES = { "Side Flute", "Bass Flute", "Recorder" };

	public Flutes(Double price, String brand, String material, String type) {
		super(price, brand, material);
		try {
			setfluteType(type);
		} catch (InputMismatchException e) {
			System.exit(1);
		}
	}

	public Flutes(Scanner f) throws NumberFormatException, Exception {
		super(f);
		try {
			setfluteType(f.nextLine());
		} catch (Exception e) {
			System.exit(0);
		}
	}

	public String getfluteType() {
		return fluteType;
	}

	public void setfluteType(String type) {
		boolean correct = false;
		for (int i = 0; i < AVAILABLE_TYPES.length; i++) {
			if (type.toLowerCase().compareTo(AVAILABLE_TYPES[i].toLowerCase()) == 0) {
				this.fluteType = AVAILABLE_TYPES[i];
				correct = true;
				break;
			}
		}
		if (!correct) {
			throw new InputMismatchException();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Flutes) {
			Flutes temp = (Flutes) obj;
			if (temp.getfluteType().compareTo(getfluteType()) == 0) {
				return super.equals(obj);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String str = super.toString() + " Type: " + getfluteType();
		return str;
	}
}
