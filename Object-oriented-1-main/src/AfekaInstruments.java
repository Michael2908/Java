
// Name: Michael Sieradzki	
// ID:204256093
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AfekaInstruments {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean notFound = true;
		while (notFound) {
			System.out.println("Please enter instruments file name / path:");
			File file = new File(sc.nextLine());
			try {
				Scanner f = new Scanner(file);
				ArrayList<Instruments> arr = new ArrayList<Instruments>();
				splitByType(f, arr);
				if (arr.size() > 0) {
					printInstruments(arr);
					System.out.println(" \nDifferent Instruments: " + getNumOfDifferentElements(arr) + "\n");
					System.out.println("Most Expensive Instrument: \n" + getMostExpensiveInstrument(arr).toString());
				} else
					System.out.println("There are no instruments in the store currently");
				notFound = false;
			} catch (FileNotFoundException e) {
				System.out.println("File Error! Please try again:");
			}
		}
	}

	public static void printInstruments(ArrayList<Instruments> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i).toString());
		}
	}

	public static Instruments getMostExpensiveInstrument(ArrayList<Instruments> arr) {
		Instruments mostExpensiveInsrument = arr.get(0);
		for (int i = 0; i < arr.size(); i++) {
			if (((Instruments) arr.get(i)).getPrice() > mostExpensiveInsrument.getPrice()) {
				mostExpensiveInsrument = (Instruments) arr.get(i);
			}
		}
		return mostExpensiveInsrument;
	}

	public static int getNumOfDifferentElements(ArrayList<Instruments> arr) {
		ArrayList<Instruments> temp = new ArrayList<Instruments>(addAllInstruments(arr));
		for (int i = 0; i < temp.size(); i++) {
			for (int j = i + 1; j < temp.size(); j++) {
				if (temp.get(i).equals(temp.get(j))) {
					temp.remove(j--);
				}
			}
		}
		return temp.size();
	}

	public static ArrayList<Instruments> addAllInstruments(ArrayList<Instruments> arr) {
		ArrayList<Instruments> temp = new ArrayList<Instruments>();
		for (int i = 0; i < arr.size(); i++) {
			temp.add(arr.get(i));
		}
		return temp;
	}

	public static ArrayList<Instruments> splitByType(Scanner f, ArrayList<Instruments> arr) {
		int guitarNumber = Integer.parseInt(f.nextLine());
		try {
			for (int i = 0; i < guitarNumber; i++) {
				arr.add(new Guitars(f));
			}
			int bassGuitarNumber = Integer.parseInt(f.nextLine());
			for (int i = 0; i < bassGuitarNumber; i++) {
				arr.add(new Bass(f));
			}
			int flutesNumber = Integer.parseInt(f.nextLine());
			for (int i = 0; i < flutesNumber; i++) {
				arr.add(new Flutes(f));
			}
			int saxophoneNumber = Integer.parseInt(f.nextLine());
			for (int i = 0; i < saxophoneNumber; i++) {
				arr.add(new Saxophone(f));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		f.close();
		System.out.println("Instruments loaded from file successfully! \n");
		return arr;
	}
}
