package company.gonapps.loghut.comparator;

import java.util.Comparator;

public class YearComparator implements Comparator<String> {
	@Override
	public int compare(String year1, String year2) {
		return (new Integer(year1) - new Integer(year2)) * -1;
	}
}
