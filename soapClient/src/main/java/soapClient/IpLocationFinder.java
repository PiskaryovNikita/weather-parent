package soapClient;

import com.thomas_bayer.blz.BLZService;
import com.thomas_bayer.blz.DetailsType;

public class IpLocationFinder {
	public static void main(String[] args) {
		try {
			BLZService service = new BLZService();
			DetailsType bank = service.getBLZServiceSOAP11PortHttp().getBank("Keine Bank");
			System.out.println(bank.getBezeichnung());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
