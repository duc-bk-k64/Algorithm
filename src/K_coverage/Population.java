package K_coverage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Population {
	public ArrayList<Individual> population;
	public static int size =100;
	public Individual individual = new Individual();
	public Random random = new Random();
	public ArrayList<Double> p;

	public void init() {
		ArrayList<Individual> list = new ArrayList<>();
		for (int i = 0; i < size; i++)
			list.add(individual.create());
		this.population = list;
		individual.readData(null);

	}

	public void crossover(double pc) {
		for (int t = 0; t < size / 4; t++) {
			double x = random.nextDouble();
			if (x < pc) {
				x = random.nextDouble();
				int h = 0, k = 0; // h,k denote individual are selected

				for (int i = 1; i < size; i++) {
					if (x > this.p.get(i - 1) && x <= this.p.get(i))
						h = i;
				}

				x = random.nextDouble();

				for (int i = 1; i < size; i++) {
					if (x > this.p.get(i - 1) && x <= this.p.get(i))
						k = i;
				}

				Individual individual1 = this.population.get(h).crossover(this.population.get(k));
				Individual individual2 = this.population.get(k).crossover(this.population.get(h));
				this.population.add(individual1);
				this.population.add(individual2);
			}
		}

	}

	public void mutation(double pm) {
		for (int i = 0; i < size / 4; i++) {
			double x = random.nextDouble();
			if (x < pm) {
				int h = random.nextInt(size - 1);
				Individual individual1 = this.population.get(h).mutation();
				this.population.add(individual1);
			}
		}
	}

	public void caculateProbaly() {
		ArrayList<Double> probaly = new ArrayList<>();
		double sum = 0;
		for (int i = 0; i < this.population.size(); i++)
			sum += this.population.get(i).fitness();
		probaly.add(this.population.get(0).fitness() / sum);
		for (int i = 1; i < this.population.size(); i++) {
			probaly.add(probaly.get(i - 1) + this.population.get(i).fitness() / sum);
		}
		this.p = probaly;
		// System.out.print(probaly.get(this.population.size()-2));
	}

	public void select() {
		Collections.sort(this.population, new Comparator<Individual>() {
			@Override
			public int compare(Individual p, Individual q) {
				Double a = p.fitness();
				Double b = q.fitness();
				if (a.compareTo(b) > 0)
					return -1;
				else if (a.compareTo(b) == 0)
					return 0;
				else
					return 1;
			}

		});
		ArrayList<Individual> childPopulation = new ArrayList<>(); //select best individual for child population
		for (int i = 0; i < size; i++)
			childPopulation.add(this.population.get(i));
		this.population = childPopulation;

	}

	public Individual getBest() {
		Individual bestIndividual = this.population.get(0);
		double best = bestIndividual.fitness();
		for (int i = 0; i < this.population.size(); i++) {
			if (this.population.get(i).fitness() > best) {
				bestIndividual = this.population.get(i);
				best = bestIndividual.fitness();
			}
		}
		return bestIndividual;
	}

	public void print() {
		System.out.println("Size:" + this.population.size());
		for (int i = 0; i < this.population.size(); i++)
			this.population.get(i).print();
	}

//	public static void main(String args[]) {
//		Population population = new Population();
//		population.init();
//		population.caculateProbaly();
//		population.crossover(0.8);
//		population.mutation(0.2);
//		population.print();
//	}

}
