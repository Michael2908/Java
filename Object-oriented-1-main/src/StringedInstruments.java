import java.util.Scanner;

public class StringedInstruments extends Instruments {
	private int stringNumber;

	public StringedInstruments(Scanner f) throws NumberFormatException, Exception {
		super(f);
		setStringNumber(Integer.parseInt(f.nextLine()));

	}

	public StringedInstruments(Double price, String brand, int stringNumber) {
		super(price, brand);
		setStringNumber(stringNumber);
	}

	public int getStringNumber() {
		return stringNumber;
	}

	public void setStringNumber(int stringNumber) {
		this.stringNumber = stringNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (this instanceof StringedInstruments) {
			StringedInstruments temp = (StringedInstruments) obj;
			if (temp.getStringNumber() == getStringNumber()) {
				return super.equals(obj);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String str = super.toString() + "Number Of Strings: " + getStringNumber() + "|";
		return str;
	}
}
