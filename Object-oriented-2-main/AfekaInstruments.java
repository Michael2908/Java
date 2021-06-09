
// Name: Michael Sireadski Id : 204256093
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AfekaInstruments {
	final static String STR = new String(new char[73]).replace("\0", "-");

	public static File getInstrumentsFileFromUser(Scanner consoleScanner) {
		boolean stopLoop = true;
		File file;
		do {
			System.out.println("Please enter instruments file name / path:");
			String filepath = consoleScanner.nextLine();
			file = new File(filepath);
			stopLoop = file.exists() && file.canRead();
			if (!stopLoop)
				System.out.println("\nFile Error! Please try again\n\n");
		} while (!stopLoop);
		return file;
	}

	public static void loadInstrumentsFromFile(File file, ArrayList<MusicalInstrument> allInstruments) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			addAllInstruments(allInstruments, loadGuitars(scanner));
			addAllInstruments(allInstruments, loadBassGuitars(scanner));
			addAllInstruments(allInstruments, loadFlutes(scanner));
			addAllInstruments(allInstruments, loadSaxophones(scanner));
		} catch (InputMismatchException | IllegalArgumentException ex) {
			System.err.println("\n" + ex.getMessage());
			System.exit(1);
		} catch (FileNotFoundException ex) {
			System.err.println("\nFile Error! File was not found");
			System.exit(2);
		} finally {
			scanner.close();
		}
		System.out.println("\nInstruments loaded from file successfully!\n");
	}

	public static ArrayList<Guitar> loadGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Guitar> guitars = new ArrayList<Guitar>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			guitars.add(new Guitar(scanner));
		return guitars;
	}

	public static ArrayList<Bass> loadBassGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Bass> bassGuitars = new ArrayList<Bass>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			bassGuitars.add(new Bass(scanner));
		return bassGuitars;
	}

	public static ArrayList<Flute> loadFlutes(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Flute> flutes = new ArrayList<Flute>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			flutes.add(new Flute(scanner));
		return flutes;
	}

	public static ArrayList<Saxophone> loadSaxophones(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Saxophone> saxophones = new ArrayList<Saxophone>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			saxophones.add(new Saxophone(scanner));
		return saxophones;
	}

	public static <R extends MusicalInstrument> void addAllInstruments(ArrayList<MusicalInstrument> instruments,
			ArrayList<R> moreInstruments) {
		for (int i = 0; i < moreInstruments.size(); i++) {
			instruments.add(moreInstruments.get(i));
		}
	}

	public static <R extends MusicalInstrument> void printInstruments(ArrayList<R> instruments) {
		for (int i = 0; i < instruments.size(); i++)
			System.out.println(instruments.get(i));
	}

	public static <R extends MusicalInstrument> int getNumOfDifferentElements(ArrayList<R> instruments) {
		int numOfDifferentInstruments;
		ArrayList<R> differentInstruments = new ArrayList<R>();
		System.out.println();
		for (int i = 0; i < instruments.size(); i++) {
			if (!differentInstruments.contains((instruments.get(i)))) {
				differentInstruments.add(instruments.get(i));
			}
		}
		if (differentInstruments.size() == 1)
			numOfDifferentInstruments = 0;
		else
			numOfDifferentInstruments = differentInstruments.size();
		return numOfDifferentInstruments;
	}

	public static <R extends MusicalInstrument> MusicalInstrument getMostExpensiveInstrument(ArrayList<R> instruments) {
		double maxPrice = 0;
		MusicalInstrument mostExpensive = instruments.get(0);
		for (int i = 0; i < instruments.size(); i++) {
			MusicalInstrument temp = instruments.get(i);
			if (temp.getPrice().doubleValue() > maxPrice) {
				maxPrice = temp.getPrice().doubleValue();
				mostExpensive = temp;
			}
		}
		return mostExpensive;
	}

	@SuppressWarnings("unchecked")
	public static <T extends MusicalInstrument, R extends T> void startInventoryMenu(ArrayList<R> arr, Scanner sc,
			AfekaInventory<T> inv) {
		final String y = "Y", n = "N";
		String str;
		boolean end = false, confirmed = false;
		char ch;
		while (!end) {
			sc.nextLine();
			printMenu();
			System.out.println("Your Option:");
			ch = sc.next().trim().charAt(0);
			sc.nextLine();
			switch (ch) {
			case '1':
				inv.addAllStringInstruments(arr, inv.getAllInstruments());
				break;
			case '2':
				inv.addAllWindInstruments(arr, inv.getAllInstruments());
				break;
			case '3':
				inv.SortByBrandAndPrice(inv.getAllInstruments());
				break;
			case '4': {
				int in = 0;
				MusicalInstrument result;
				String brand;
				Number price;
				if (inv.getAllInstruments().size() > 0) {
					System.out.println("SEARCH INSTRUMENT:");
					System.out.println("Brand:");
					brand = sc.nextLine();
					System.out.println("Price:");
					try {
						price = Double.parseDouble(sc.nextLine());
					} catch (Exception e) {
						price = 0;
					}
					try {
						in = inv.binnarySearchByBrandAndPrice(inv.getAllInstruments(), price, brand);
						if (in < 0) {
							System.out.println("Instrument Not Found");
							result = null;
						}
					} catch (Error e) {
						System.err.println(e.getMessage());
						System.out.println(" ");
						result = null;
					}
				} else {
					System.out.println("Please load instruments");
					result = null;
				}
				result = inv.getAllInstruments().get(in);

				if (result != null)
					System.out.println("Result:  \n" + result);

				break;
			}
			case '5':
				if (inv.getAllInstruments().size() > 0) {
					int in = 0;
					R result = null;
					String brand;
					Number price;
					if (inv.getAllInstruments().size() > 0) {
						System.out.println("SEARCH INSTRUMENT:");
						System.out.println("Brand:");
						brand = sc.nextLine();
						System.out.println("Price:");
						try {
							price = Double.parseDouble(sc.nextLine());
						} catch (Exception e) {
							price = 0;
						}
						try {
							in = inv.binnarySearchByBrandAndPrice(inv.getAllInstruments(), price, brand);
							if (in < 0) {
								System.out.println("Instrument Not Found");
							}
						} catch (Error e) {
							System.err.println(e.getMessage());
							System.out.println(" ");
						}
					} else {
						System.out.println("Please load instruments");
					}
					try {
						result = (R) inv.getAllInstruments().get(in);
					} catch (Exception e) {
						System.out.println("list not sorted");
					}
					System.out.println("Result:  \n" + result);
					if (result != null)
						System.out.println("Instrument Deleted Successfully!");
				}
				break;
			case '6':
				if (inv.getAllInstruments().size() > 0) {
					do {
						sc.nextLine();
						System.out.println("Are You Sure?(Y/N)");
						str = sc.next().trim().toUpperCase();
					} while (str != y && str != n);
					sc.nextLine();
					if (str == y || str == n) {
						confirmed = true;
					}
					if (confirmed) {
						boolean removed = inv.removeAll(inv.getAllInstruments());
						if (removed) {
							System.out.println("All Instruments Deleted Successfully!");
						}
					}
				}
				break;
			case '7':
				System.out.println(STR + " \nAFEKA MUSICAL INSTRUMENTS INVENTORY" + " \n" + STR);
				if (inv.getAllInstruments().size() > 0)
					printInstruments(inv.getAllInstruments());
				else
					System.out.println("There Is No Instruments To Show");
				System.out.println(inv.toString());
				break;
			default:
				end = true;
				break;
			}
		}
		System.out.println("Finished!");
	}

	public static void printMenu() {
		System.out.println(STR + " \nAFEKA MUSICAL INSTRUMENT INVENTORY MENU" + " \n" + STR);
		System.out.println("1.Copy All String Instruments To Inventory");
		System.out.println("2.Copy All Wind Instruments To Inventory");
		System.out.println("3.Sort Instruments By Brand And Price");
		System.out.println("4.Search Instrument By Brand And Price");
		System.out.println("5.Delete Instrument");
		System.out.println("6.Delete all Instruments");
		System.out.println("7.Print Inventory Instruments");
		System.out.println("Choose your option or any other key to EXIT");
	}

	public static void main(String[] args) {
		ArrayList<MusicalInstrument> allInstruments = new ArrayList<MusicalInstrument>();
		Scanner consoleScanner = new Scanner(System.in);
		File file = getInstrumentsFileFromUser(consoleScanner);
		loadInstrumentsFromFile(file, allInstruments);
		if (allInstruments.size() == 0) {
			System.out.println("There are no instruments in the store currently");
			return;
		}
		printInstruments(allInstruments);
		int different = getNumOfDifferentElements(allInstruments);
		System.out.println("\n\nDifferent Instruments: " + different);
		MusicalInstrument mostExpensive = getMostExpensiveInstrument(allInstruments);
		System.out.println("\n\nMost Expensive Instrument:\n" + mostExpensive);
		AfekaInventory<MusicalInstrument> inventory = new AfekaInventory<MusicalInstrument>();
		startInventoryMenu(allInstruments, consoleScanner, inventory);
		consoleScanner.close();
	}
}
