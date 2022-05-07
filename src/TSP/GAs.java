package TSP;
import java.time.Duration;
import java.time.Instant;

public class GAs {
	public GAs() {
	}
	public void algorithms(int max_interation, double rc,double rm) throws Exception {
		for(int i=0;i<30;i++) {
			Instant timeInstant=Instant.now();
		Population population=new Population();
		Chromosome chromosome=new Chromosome();
		population.inti();
		int k=0;
		while(k!=max_interation) {
			population.crossover(rc);
			population.mutation(rm);
			population.select();
			k++;
			//System.out.print("Generation "+k+":");
//			population.getBest().printf();
//			System.out.println(chromosome.fitness(population.getBest()));
		}
		population.getBest().printf();
		System.out.println(-chromosome.fitness(population.getBest())+" Time:"+Duration.between(timeInstant, Instant.now()));
		}
		
	}
	public static void main(String args[]) throws Exception {
		GAs gAs=new GAs();
		gAs.algorithms(2000,0.8,0.05);
	}

}
