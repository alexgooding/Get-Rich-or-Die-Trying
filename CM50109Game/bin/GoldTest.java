public class GoldTest{
	
	public static void main(String[] args){

		Location testGoldLocation = new Location(4, 4, 2);
		Gold testGold = new Gold(testGoldLocation, 3);
		System.out.println("The location of testGold is: ");
		testGold.getItemLocation().printLocation();
		System.out.println("The amount of gold in testGold is: " + testGold.getGoldAmount());
	}
	
}