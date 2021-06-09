import java.util.InputMismatchException;
import java.util.Scanner;

public class Guitars extends StringedInstruments {
	private String GuitarTypes;
	private final String[] AVAILABLE_TYPES = { "Classic", "Electric", "Acoustic" };

	final static int MIN = 6;
	final int MAX = 8;

	public Guitars(Double price, String brand, int stringNumber, String type) {
		super(price, brand, stringNumber);
		try {
			setGuitarTypes(type);
		} catch (Exception e) {
			System.exit(0);
		}
		try {
			validStringNumber(stringNumber, type);
			setStringNumber(stringNumber);
		} catch (InputMismatchException e) {
			System.exit(0);
		}
	}

	public Guitars(Scanner f) throws NumberFormatException, Exception {
		super(f);
		String str = f.nextLine();
		try {
			setGuitarTypes(str);
		} catch (Exception e) {
			System.exit(0);
		}
		try {
			validStringNumber(getStringNumber(), getGuitarTypes());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public String getGuitarTypes() {
		return GuitarTypes;
	}

	public void setGuitarTypes(String guitarType) throws Exception {
		boolean correct = false;
		for (int i = 0; i < AVAILABLE_TYPES.length; i++) {
			if (guitarType.toLowerCase().trim().compareTo(AVAILABLE_TYPES[i].toLowerCase()) == 0) {
				this.GuitarTypes = AVAILABLE_TYPES[i];
				correct = true;
				break;
			}
		}
		if (!correct) {
			throw new Exception();
		}
	}

	public boolean validStringNumber(int stringNumber, String type) {
		if (type.toLowerCase().compareTo(AVAILABLE_TYPES[0].toLowerCase()) == 0
				|| type.toLowerCase().compareTo(AVAILABLE_TYPES[2].toLowerCase()) == 0) {
			if (stringNumber != MIN) {
				throw new InputMismatchException(type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase()
						+ " Guitar Have " + MIN + " Strings , Not " + stringNumber);
			}
		} else if (type.toLowerCase().compareTo(AVAILABLE_TYPES[1].toLowerCase()) == 0) {

			if (MIN > stringNumber || stringNumber > MAX) {
				throw new InputMismatchException(
						"Electric Guitar Have " + MIN + "-" + MAX + "Strings , Not " + stringNumber);
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Guitars) {
			Guitars temp = (Guitars) obj;
			if (temp.getGuitarTypes().compareTo(getGuitarTypes()) == 0) {
				return super.equals(obj);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String str = super.toString() + " Type: " + getGuitarTypes();
		return str;

	}
}
