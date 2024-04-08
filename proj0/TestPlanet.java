public class TestPlanet {
  public static void main(String[] args) {
    checkPlanet();
  }

    /**
     *  Checks whether or not two Doubles are equal and prints the result.
     *
     *  @param  expected    Expected double
     *  @param  actual      Double received
     *  @param  label       Label for the 'test' case
     *  @param  eps         Tolerance for the double comparison.
     */
  private static void checkEquals(double expected, double actual, String label, double eps) {
      if (Math.abs(expected - actual) <= eps * Math.max(expected, actual)) {
          System.out.println("PASS: " + label + ": Expected " + expected + " and you gave " + actual);
      } else {
          System.out.println("FAIL: " + label + ": Expected " + expected + " and you gave " + actual);
      }
  }

  private static void checkPlanet() {
    System.out.println("Checking Planet ...");

    Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
    Planet p2 = new Planet(4.0, 5.0, 5.0, 2.0, 10.0, "jupiter.gif");

    checkEquals(5.0, p1.calcDistance(p2), "calcDistance()", 0.01);
    checkEquals(1.33e-10, p1.calcForceExertedBy(p2), "calcForceExertedBy()", 0.01);
    checkEquals(8.00e-11, p1.calcForceExertedByX(p2), "calcForceExertedByX()", 0.01);
    checkEquals(1.067e-10, p1.calcForceExertedByY(p2), "calcForceExertedByY()", 0.01);

    p2.update(1, 2, 6);
    checkEquals(5.2, p2.xxVel, "xxVel update()", 0.01);
    checkEquals(2.6, p2.yyVel, "yyVel update()", 0.01);
    checkEquals(9.2, p2.xxPos, "xxPos update()", 0.01);
    checkEquals(7.6, p2.yyPos, "yyPos update()", 0.01);
  }

}
