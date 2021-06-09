import java.util.ArrayList;

public class AfekaInventory<M extends MusicalInstrument> implements InventoryManagement<M> {
	private boolean sorted;
	private double priceTotal;
	private ArrayList<M> instrumentList;

	public AfekaInventory(ArrayList<M> instrumentsList) {
		this.instrumentList = new ArrayList<M>();
		setAllInstruments(instrumentsList);
		setPriceTotal();
		setSorted(false);

	}

	public AfekaInventory() {
		this.instrumentList = new ArrayList<M>();
		setSorted(false);
	}

	public ArrayList<M> getAllInstruments() {
		return instrumentList;
	}

	public void setAllInstruments(ArrayList<M> instrumentsList) {
		this.instrumentList = instrumentsList;
	}

	public double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal() {
		double total = 0;
		for (int i = 0; i < instrumentList.size(); i++)
			total += instrumentList.get(i).getPrice().doubleValue();
		this.priceTotal = total;
	}

	public boolean sorted() {
		return sorted;
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	public void addInstrument(M add) {
		this.instrumentList.add(add);
		setPriceTotal();
	}

	public void addAllWindInstruments(ArrayList<? extends M> start, ArrayList<? super M> end) {
		for (int i = 0; i < start.size(); i++) {
			if (start.get(i) instanceof WindInstrument) {
				end.add(start.get(i));
			}
			setPriceTotal();
			setSorted(false);
		}
		System.out.println("All Wind Instruments Added Successfully!");
	}

	public void addAllStringInstruments(ArrayList<? extends M> start, ArrayList<? super M> end) {
		for (int i = 0; i < start.size(); i++) {
			if (start.get(i) instanceof StringInstrument) {
				end.add(start.get(i));
			}
			setPriceTotal();
			setSorted(false);
		}
		System.out.println("All String Instruments Added Successfully!");
	}

	public boolean removeInstrument(ArrayList<M> inventoryList, M remove) {
		boolean deleted = false;
		for (int i = 0; i < inventoryList.size(); i++) {
			if (remove.equals(inventoryList.get(i))) {
				inventoryList.remove(i);
				deleted = true;
				setPriceTotal();
			}
		}
		return deleted;
	}

	@Override
	public boolean removeAll(ArrayList<M> inventoryList) {
		int temp = inventoryList.size();
		for (int i = 0; i < temp; i++) {
			inventoryList.remove(inventoryList.size() - 1);
		}
		setPriceTotal();
		setSorted(false);
		return (temp == 0);
	}

	public int binnarySearchByBrandAndPrice(ArrayList<M> inventoryList, Number price, String brand) {
		int temp = -1;
		double value;
		if (sorted() == true) {
			int low = 0, high = inventoryList.size() - 1, mid, i;
			while (low <= high) {
				mid = (low + high) / 2;
				i = brand.compareToIgnoreCase(inventoryList.get(mid).getBrand());
				if (i > 0)
					low = mid + 1;
				else if (i < 0)
					high = mid - 1;
				else {
					value = inventoryList.get(mid).getPrice().doubleValue() - price.doubleValue();
					if (value < 0) {
						temp = mid;
						low = mid + 1;
					} else if (value > 0) {
						temp = mid;
						high = mid - 1;
					} else {
						return mid;
					}
				}
			}
		} else
			System.out.println("Please sort the inventory first before searching");
		return temp;
	}
	
	public void SortByBrandAndPrice(ArrayList<M> sorting) {
		for (int i = 0; i < sorting.size(); i++) {
			for (int j = 0; j < (sorting.size() - 1); j++) {
				if (sorting.get(j).compareTo(sorting.get(j + 1)) > 0) {
					M temp = sorting.get(j);
					sorting.set(j, sorting.get(j + 1));
					sorting.set(j + 1, temp);
				}
			}
		}
		System.out.println("Instruments Sorted Successfully!");
		setSorted(true);
	}

	public void addInstrument(ArrayList<M> inventoryList, M added) {
		inventoryList.add(added);
		setSorted(false);
		setPriceTotal();
	}

	@Override
	public String toString() {

		return String.format(" Total Price:%-15.2f Sorted:%8s", getPriceTotal(), sorted());

	}
}

interface InventoryManagement<I> {
	void addAllWindInstruments(ArrayList<? extends I> start, ArrayList<? super I> end);

	boolean removeInstrument(ArrayList<I> inventoryList, I remove);

	void addAllStringInstruments(ArrayList<? extends I> start, ArrayList<? super I> end);

	int binnarySearchByBrandAndPrice(ArrayList<I> inventoryList, Number price, String brand);

	void SortByBrandAndPrice(ArrayList<I> sorting);

	void addInstrument(ArrayList<I> inventoryList, I added);

	boolean removeAll(ArrayList<I> inventoryList);
}