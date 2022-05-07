package K_coverage;

import java.util.ArrayList;
import java.util.Random;

public class Individual {
	// public static ArrayList<Sensor> data;
	public static int K = 3; // number of potentiol position
	public static int N = 2; // number of targets
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

		Target target1 = new Target();
		target1.setX(0.5);
		target1.setY(0.0);
		Target target2 = new Target();
		target2.setX(0.0);
		target2.setY(0.5);
		ArrayList<Target> listTargets = new ArrayList<>();
		listTargets.add(target1);
		listTargets.add(target2);
		Individual.setTargets(listTargets);
		Sensor sensor1 = new Sensor(0.0, 0.0, 1.0, 2.0);
		Sensor sensor2 = new Sensor(1.0, 0.0, 1.0, 2.0);
		Sensor sensor3 = new Sensor(0.0, 3.0, 1.0, 2.0);
		ArrayList<Sensor> list = new ArrayList<>();
		list.add(sensor1);
		list.add(sensor2);
		list.add(sensor3);
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
			return Individual.k - cost;
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
			return Individual.m - cost;

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

	public static void main(String args[]) {
		Individual individual = new Individual();
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(1);
		list.add(1);
		individual.setChromosome(list);
		individual.print();
		individual.readData(null);
		System.out.println(individual.fitness());

//		Individual gen1=individual.create();
//		Individual gen2=individual.create();
//		gen1.print();
//		gen2.print();
//		gen1.crossover(gen2).print();
//		gen1.mutation().print();
//		System.out.println(gen1.fitness());
//		System.out.println(gen2.fitness());

	}

}
