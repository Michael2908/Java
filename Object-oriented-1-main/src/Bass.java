import java.util.InputMismatchException;
import java.util.Scanner;

public class Bass extends StringedInstruments {
	final int MIN = 4;
	final int MAX = 6;
	private String fretless;

	public Bass(Double price, String brand, int stringNumber, String fretless) {
		super(price, brand, stringNumber);
		try {
			validNumberOfStrings(stringNumber);
			setFretless(fretless);
		} catch (Exception e) {
			System.exit(1);
		}
	}

	public Bass(Scanner f) throws NumberFormatException, Exception {
		super(f);
		try {
			if (validNumberOfStrings(getStringNumber())) {
				String str = f.nextLine();
				setFretless(str);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	public String getFretless() {
		return fretless;
	}

	public void setFretless(String fretless) {
		if (fretless.toLowerCase().compareTo("true") == 0) {
			this.fretless = "Yes";
		} else if (fretless.toLowerCase().compareTo("false") == 0) {
			this.fretless = "No";
		} else
			throw new InputMismatchException(
					"Whether a bass is fretless or not is boolean, any other string than 'True' or 'False' is not acceptable");
	}

	public boolean validNumberOfStrings(int stringNumber) {
		if (MIN > stringNumber || stringNumber > MAX) {
			throw new InputMismatchException("Bass number of strings is a number between 4 and 6");
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bass) {
			Bass temp = (Bass) obj;
			if (temp.getFretless().compareTo(getFretless()) == 0) {
				return super.equals(obj);
			}
		}
		return false;
	}

	public String toString() {
		String str = super.toString() + " Fretless: " + getFretless();
		return str;
	}
}
