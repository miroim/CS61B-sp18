public class Planet {
  double xxPos; // current x position
  double yyPos; // current y position
  double xxVel; // current velocity in the x direction 
  double yyVel; // current velocity in the y direction 
  double mass;  // mass
  String imgFileName; 

  public Planet(double xP, double yP, double xV, double yV, double m, String img) {
    this.xxPos = xP;
    this.yyPos = yP;
    this.xxVel = xV;
    this.yyVel = yV;
    this.mass = m;
    this.imgFileName = img;
  }

  public Planet(Planet p) {
    this.xxPos = p.xxPos;
    this.yyPos = p.yyPos;
    this.xxVel = p.xxVel;
    this.yyVel = p.yyVel;
    this.mass = p.mass;
    this.imgFileName = p.imgFileName;
  }

  public double calcDistance(Planet p) {
    /* distance between points as:
     *          _______________________
     *         /         2            2
     *   d = \/ (x1 - x0)  + (y1 - y0)
    */
    double distance;
    distance = Math.sqrt(Math.pow(p.xxPos-this.xxPos, 2) + Math.pow(p.yyPos-this.yyPos, 2));
    return distance;
  }
}
