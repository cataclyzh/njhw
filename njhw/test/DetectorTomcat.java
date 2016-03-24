public class DetectorTomcat {
	public static void keepTomcatAlive() throws Exception {
		System.out.println("...");
		try {
			Runtime.getRuntime().exec("/home/njhw/test/tomcatMonitor.sh");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		while (true) {
			try {
				DetectorTomcat.keepTomcatAlive();
				Thread.sleep(60*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
