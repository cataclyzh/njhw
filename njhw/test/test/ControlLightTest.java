package test;
import obix.Bool;
import obix.Obj;
import obix.Uri;
import obix.net.ObixSession;


public class ControlLightTest {

	public static void main(String[] args) {
		controlLight("1415", true);
	}
	
	private static void controlLight(String name, boolean isOpen){
		String uri = "http://10.250.250.168";
		String username = "admin";
		String password = "admin123";
		String operUri = "http://10.250.250.168/obix/config/Drivers/NxModbusTcpNetwork/ModbusTcpDevice/points/BooleanWritable"+name+"/set";
		Uri u = new Uri(uri);
		ObixSession os = new ObixSession(u, username, password);
		//"http://10.250.250.168/obix/config/Drivers/NxModbusTcpNetwork/ModbusTcpDevice/points/BooleanWritable155/set/
		try {
			Obj result = os.invoke(new Uri(operUri), new Bool(isOpen));
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
