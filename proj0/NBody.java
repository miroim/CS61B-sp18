public class NBody {

  public static double readRadius(String filename) {
    // read radius from file
    In in = new In(filename);
    int n = in.readInt();
    double r = in.readDouble();
    return r;
  }

  public static Planet[] readPlanets(String filename) {
    In in = new In(filename);
    int n = in.readInt();
    double r = in.readDouble();
    Planet[] plants = new Planet[n];
    for(int i=0; i<n; i++) {
      plants[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
    }
    return plants;
  }

  public static void main(String[] args) {
    if(args.length != 3) {
      System.err.println("Usage: NBody <T> <dt> <filename>.");
    }
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];

    // read file to get universe radius and planets
    double r = readRadius(filename);
    Planet[] planets = readPlanets(filename);

  }
}
