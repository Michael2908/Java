import java.util.InputMismatchException;
import java.util.Scanner;

public class Saxophone extends WoodwindInstruments {
	private final String MATERIAL = "Metal";

	public Saxophone(Double price, String brand, String material) {
		super(price, brand, material);
		boolean material1 = false;
		try {
			String mats = getMaterial();
			if (mats == MATERIAL) {
				material1 = true;
			}
			if (mats != MATERIAL) {
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.exit(0);
			if (material1) {
				setMaterial(material);
			}
		}
	}

	public Saxophone(Scanner f) throws NumberFormatException, Exception {
		super(f);
		try {
			String mats = getMaterial();
			if (mats != MATERIAL) {
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.exit(0);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Saxophone) {
			return super.equals(obj);
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
