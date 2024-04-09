public class Planet {
  public double xxPos; // current x position
  public double yyPos; // current y position
  public double xxVel; // current velocity in the x direction 
  public double yyVel; // current velocity in the y direction 
  public double mass;  // mass
  public String imgFileName; 

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
     *         _________________________
     *   d = \/ (x1 - x0)²  + (y1 - y0)²
    */
    double distance;
    distance = Math.sqrt(Math.pow(p.xxPos-this.xxPos, 2) + Math.pow(p.yyPos-this.yyPos, 2));
    return distance;
  }

  public double calcForceExertedBy(Planet p) {
    /* force 
     * m1: the mass of planet1
     * m2: the mass of planet2
     * 
     *      G * m1 * m2
     * F = --------------
     *          d²
     */
    double G = 6.67e-11; //the gravitational constant
    double force = G * this.mass * p.mass / Math.pow(this.calcDistance(p), 2);

    return force;
  }

  public double calcForceExertedByX(Planet p) {
    /* force exerted in the X direction
     * 
     *        F * dx
     * Fx = -----------
     *          d
     */
    double force_x;
    force_x = this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
    return force_x;
  }

  public double calcForceExertedByY(Planet p) {
    /* force exerted in the Y direction
     * 
     *        F * dy
     * Fy = -----------
     *          d
     */
    double force_y;
    force_y = this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
    return force_y;
  }

  public double calcNetForceExertedByX(Planet[] planets) {
    /* F_net_x can be calculated by summing the x-components of all pairwise forces
     * 
     * force_net_x = force_1_x + ... + force_i_x;
     */
    double force_net_x = 0;
    for(Planet p : planets) {
      if(this.equals(p)) continue;
      else {
        force_net_x += this.calcForceExertedByX(p);
      }
    }
    return force_net_x;
  }

  public double calcNetForceExertedByY(Planet[] planets) {
    /* F_net_y can be calculated by summing the y-components of all pairwise forces
     * 
     * force_net_y = force_1_y + ... + force_i_y;
     */
    double force_net_y = 0.0;
    for(Planet p : planets) {
      if(this.equals(p)) continue;
      else {
        force_net_y += this.calcForceExertedByY(p);
      }
    }
    return force_net_y;
  }

  public void update(double dt, double fx, double fy) {
    /* 1. Calculate the acceleration using the provided fx and fy
     *      a = F / m
     *
     * 2. Calculate the new velocity by using the acceleration and current velocity
     *      v_new = v_old + dt * a
     *
     * 3. Calculate the new position by using the velocity computed in step 2 and the current position
     *      p_new = p_old + dt * v_new
     */

    double a_x, a_y;
    double xxVel_new, yyVel_new;
    double xxPos_new, yyPos_new;

    // calculate acceleration
    a_x = fx / this.mass;
    a_y = fy / this.mass;

    // calculate new velocity
    xxVel_new = this.xxVel + dt * a_x;
    yyVel_new = this.yyVel + dt * a_y;

    // calculate new position
    xxPos_new = this.xxPos + dt * xxVel_new;
    yyPos_new = this.yyPos + dt * yyVel_new;

    // update velocity and position
    this.xxVel = xxVel_new;
    this.yyVel = yyVel_new;
    this.xxPos = xxPos_new;
    this.yyPos = yyPos_new;
  }

  public void draw() {
    String imagePath = "images/" + this.imgFileName;
    StdDraw.picture(this.xxPos, this.yyPos, imagePath);
  }
}
