package company.gonapps.loghut.comparator;

import java.util.Comparator;

public class MonthComparator implements Comparator<String> {
	@Override
	public int compare(String month1, String month2) {
		return (new Integer(month1) - new Integer(month2)) * -1;
	}
}
