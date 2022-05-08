package K_coverage;

import java.util.ArrayList;
import java.util.Random;

public class Individual {
	// public static ArrayList<Sensor> data;
	public static int K =10; // number of potentiol position
	public static int N = 5; // number of targets
	private ArrayList<Integer> chromosome;
	public static ArrayList<Target> targets;
	public static ArrayList<Sensor> position;
	public static int k = 2; // k-coverage
	public static int m = 2; // m-connected

	public Individual() {

	}

	public ArrayList<Integer> getChromosome() {
		return chromosome;
	}

	public void setChromosome(ArrayList<Integer> chromosome) {
		this.chromosome = chromosome;
	}

	public Random random = new Random();

	public Individual(ArrayList<Integer> chromosome) {
		this.chromosome = chromosome;
	}

	public Individual create() {
		Double x;
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < K; i++) {
			x = random.nextDouble();
			if (x > 0.3 && x < 0.9)
				list.add(1);
			else
				list.add(0);
		}
		Individual individual = new Individual(list);
		return individual;

	}

	public Individual crossover(Individual m) {
		int h = random.nextInt(K - 1);
		// System.out.println(h);
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i <= h; i++)
			list.add(this.chromosome.get(i));
		for (int i = h + 1; i < K; i++)
			list.add(m.getChromosome().get(i));
		Individual individual = new Individual(list);
		return individual;
	}

	public Individual mutation() {
		int h = random.nextInt(K - 1);
		// System.out.println(h);
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < K; i++)
			list.add(this.getChromosome().get(i));
		list.set(h, 0);
		Individual individual = new Individual(list);
		return individual;
	}

	public void print() {
		for (int i = 0; i < K; i++) {
			System.out.print(this.chromosome.get(i) + " ");
		}
		System.out.println();
	}

	public void readData(String url) {
		// read data from file txt
        ArrayList<Target> listTargets = new ArrayList<>();
        for(int i=0;i<N;i++) {
		Target target = new Target();
		target.setX(i+0.5);
		target.setY((double)i);
		listTargets.add(target);
        }
		Individual.setTargets(listTargets);
		ArrayList<Sensor> list = new ArrayList<>();
		for(int i=0;i<K;i++) {
		Sensor sensor = new Sensor((double)i+1,(double)i, 1.0, 2.0);
		list.add(sensor);
		}
		Individual.setPosition(list);
	}

	public int CovCost(int j) {
		int cost = 0;
		for (int i = 0; i < K; i++)
			if (Individual.position.get(i).Sensing(Individual.targets.get(j)) == 1 && this.chromosome.get(i) != 0) {
				cost++;
			}
		if (cost >= Individual.k)
			return Individual.k;
		else
			//return Individual.k - cost;
			return cost;
	}

	public int ComCost(int j) {
		int cost = 0;
		for (int i = 0; i < K; i++)
			if (Individual.position.get(j).communication(Individual.position.get(i)) == 1 && this.chromosome.get(i) != 0
					&& i != j) {
				cost++;
			}
		if (cost >= Individual.m)
			return Individual.m;
		else
			//return Individual.m - cost;
			return cost;

	}

	public static ArrayList<Target> getTargets() {
		return targets;
	}

	public static void setTargets(ArrayList<Target> targets) {
		Individual.targets = targets;
	}

	public static ArrayList<Sensor> getPosition() {
		return position;
	}

	public static void setPosition(ArrayList<Sensor> position) {
		Individual.position = position;
	}

	public double fitness() {
		int M = 0;
		for (int i = 0; i < K; i++) { // object 1: minimmum selcted node
			if (this.chromosome.get(i) != 0)
				M++;
		}
		double result = 1 - (float) M / K;
		int sumCov = 0;
		for (int i = 0; i < N; i++) { // object 2: k-coverage
			sumCov += this.CovCost(i);
		}
		result += (float) sumCov / (N * k);
		int sumCom = 0;
		for (int i = 0; i < K; i++) {
			if (this.chromosome.get(i) != 0)
				sumCom += this.ComCost(i);
		}
		result += (float) sumCom / (M * m);
		return result;
	}

//	public static void main(String args[]) {
//		Individual individual = new Individual();
//		ArrayList<Integer> list = new ArrayList<>();
//		list.add(1);
//		list.add(0);
//		list.add(0);
//		individual.setChromosome(list);
//		individual.print();
//		individual.readData(null);
//		System.out.println(individual.fitness());
//	}

//		Individual gen1=individual.create();
//		Individual gen2=individual.create();
//		gen1.print();
//		gen2.print();
//		gen1.crossover(gen2).print();
//		gen1.mutation().print();
//		System.out.println(gen1.fitness());
//		System.out.println(gen2.fitness());

//	}

}
