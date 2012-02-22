package trees;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LandEvolution {

	java.util.Scanner scanner 	= new java.util.Scanner(System.in);
	java.util.Random random 	= new java.util.Random(System.currentTimeMillis());
	
	List<Animal> animals 		= new ArrayList<Animal>(); 
	Map<Dimension,Plant> plants = new HashMap<Dimension,Plant>();
	
	int width = 100,height = 30;
	
	int jungleX =45, jungleY = 10, jungleWidth = 10, jungleHeight = 10;

	
	public LandEvolution(){
		animals.add( new Animal(  width << 1,  height << 1, 1000, 0 ) );
	}
	
	public void go() {

		while(true) {
			drawWorld();
			freshLine();
			String str = scanner.next();
			if( str.equals("quit")) {
				System.exit(0);
			}
			else {
				int iterations =Integer.parseInt(str);
				for(int i = 0; i < iterations ; i++ ){
					updateWorld();
				}
			}
		}
	}
	
	void randomPlant(int left, int top, int width, int height){
		plants.put( new Dimension( left + random(width), top + random(height)), null );
	}



	void updateWorld() {
		// remove if animals energy less than 0
		for(Animal animal : animals){
			turn(animal);
			move(animal);
			eat(animal);
			reproduce(animal);

		}
		addPlants();

	}
	
	int random(int limit) {
		return random.nextInt(limit);
	}

	private void addPlants() {
		randomPlant(jungleX,jungleY, jungleWidth, jungleHeight);
		randomPlant(0, 0, width, height);

	}



	private void reproduce(Animal animal) {
		// TODO Auto-generated method stub

	}



	private void eat(Animal animal) {
		// TODO Auto-generated method stub

	}

	private void move(Animal animal) {
		if( animal.direction >= 2 && animal.direction < 5){
			animal.x = (animal.x + 1 + width) % width;
		}
		else if ( animal.direction == 1 && animal.direction == 5) {
			animal.x = (animal.x +  width) % width;
		}
		else {
			animal.x = (animal.x -1 + width) % width;
		}
		
		if( animal.direction >= 0 && animal.direction < 3){
			animal.y = (animal.y - 1 + height) % height;
		}
		else if ( animal.direction >= 4 && animal.direction < 5) {
			animal.y = (animal.y +1+  height) % height;
		}
		else {
			animal.y = (animal.y  + height) % height;
		}
		animal.energy = animal.energy - 1;

	}



	private void turn(Animal animal) {
		int r = random(animal.geneSUm());
	}



	void freshLine() {

	}

	void drawWorld() {

	}


	interface Functor<T> {
		public <T> T f(T a);
	}
	interface RemoverFunctor<T> {
		public <T> boolean f(T a) ;
	}

	public static <T> List<T> map( Functor<T> f, List<T> list){
		List<T> ret = new ArrayList<T>();
		for( T t : list ){
			ret.add( f.f(t) );
		}
		return ret;
	}

	public static <T> List<T> filter( RemoverFunctor<T> f, List<T> list){
		List<T> ret = new ArrayList<T>();
		for( T t : list ){
			if( !f.f(t)) {
				ret.add( t);
			}
		}
		return ret;
	}


	public static void main(String[] args) {
		List<String> l  = map( new Functor<String>(){

			@Override
			public <String> String f(String a) {
				return (String) (a + "__"); 
			}

		}, 
		Arrays.asList( "noble", "pilot", "Edo", "Grays", "blackship", "void", "emptiness") );

		for(String s : l ){
			System.out.println(s );
		}

		l  = filter( new RemoverFunctor<String>(){
			@Override
			public <String> boolean f(String a) {
				return ((java.lang.String) a).charAt(0) == 'n'; 
			}
		}, 
		Arrays.asList( "noble", "pilot", "Edo", "Grays", "blackship", "void", "emptiness") );


		for(String s : l ){
			System.out.println(s );
		}
		
		int i = 100;
		System.out.println( i << 1);
		System.out.println( i << 2);
		System.out.println( i >> 1);
		System.out.println( i >> 2);
	}

	class Animal { 
		int x,y,direction,energy; 
		int[] genes;
		
		public Animal(int x, int y, int energy, int dir) {
			this.x = x;
			this.y = y;
			this.energy = energy;
			this.direction = dir;
			genes = new int[10];
			for(int i=0 ; i< 10; i++ ){
				genes[i] = random(10);
			}
		}
		
		int geneSUm(){
			int sum  = 0;
			for(int i : genes) {
				sum += i;
			}
			return sum;
		}
	}
	class Plant {}
}

