import java.util.*;

public class SimpleMapReduce {

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


}
