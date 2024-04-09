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

    // create background
    String backgroundPath = "images/starfield.jpg";
    StdDraw.setScale(-r,r); // set background scale
    StdDraw.picture(0, 0, backgroundPath);
    
    // draw all plants into background
    for(Planet p : planets) {
      p.draw();
    }

		StdDraw.enableDoubleBuffering();

    int time = 0;
    while(time < T) {
      int n = planets.length;
      double xForces[] = new double[n];
      double yForces[] = new double[n];

      // calculate net x and y forces for each planet and storing in xForces and yForces respectively
      for(int i=0; i<n; i++) {
        xForces[i] = planets[i].calcNetForceExertedByX(planets);
        yForces[i] = planets[i].calcNetForceExertedByY(planets);
      }

      // update each planet
      for(int i=0; i<n; i++) {
        planets[i].update(dt, xForces[i], yForces[i]);
      }

      // redraw background and all planets
      StdDraw.picture(0, 0, backgroundPath);
      for(Planet p : planets) {
        p.draw();
      }
      StdDraw.show();
      StdDraw.pause(100); // wait 100 milliseconds

      time += dt;
    }

    StdOut.printf("%d\n",planets.length);
    StdOut.printf("%.2e\n",r);
    for(Planet p : planets) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
    }

  }
}
