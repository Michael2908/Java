import java.util.InputMismatchException;
import java.util.Scanner;

public class WoodwindInstruments extends Instruments {
	private String material;
	private final String[] MATERIALS = { "Metal", "Wood", "Plastic" };

	public WoodwindInstruments(Double price, String brand, String material) {
		super(price, brand);
		try {
			setMaterial(material);
		} catch (Exception e) {
			System.exit(1);
		}
	}

	public WoodwindInstruments(Scanner f) throws NumberFormatException, Exception {
		super(f);
		try {
			setMaterial(f.nextLine());
		} catch (Exception e) {
			System.exit(0);
		}
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		boolean set = false;
		for (int i = 0; i < MATERIALS.length; i++) {
			if (material.toLowerCase().compareTo(MATERIALS[i].toLowerCase()) == 0) {
				this.material = MATERIALS[i];
				set = true;
				break;
			}
		}
		if (!set) {
			throw new InputMismatchException();
		}
	}

	private String setSpaces() {
		String s;
		if (getMaterial() == "Wood") {
			s = String.format("%1$-16s", "Made Of: ");
		} else {
			s = String.format("%1$-15s", "Made Of: ");
		}
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		if (this instanceof WoodwindInstruments) {
			WoodwindInstruments temp = (WoodwindInstruments) obj;
			if (temp.getMaterial().compareTo(getMaterial()) == 0) {

				return super.equals(obj);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		;
		String str = super.toString() + setSpaces() + getMaterial() + "|";

		return str;
	}

}
