package K_coverage;

public class GAs {
	public void solve(int maxIteration, double pc, double pm) {
		Population population = new Population();
		population.init();
		int k = 0;
		while (k != maxIteration) {
			k++;
			population.caculateProbaly();
			population.crossover(pc);
			population.mutation(pm);
			population.select();
			population.getBest().print();
			System.out.println("Generation " + k + " Fitness value:" + population.getBest().fitness());
		}
	}

	public static void main(String args[]) {
		GAs gAs = new GAs();
		gAs.solve(2000, 0.8, 0.2);
	}
}
