package mvc;

import java.util.Map;

public interface ElectionsListener {
	String modelShowLastElectionsResults();
	String modelShowAllBallotsInformation();
	String modelShowAllCitizensInformation();
	String modelShowAllPartiesInformation();
	String modelShowElectionsResults();
	void modelElect(Map<String, String> voters);
}
