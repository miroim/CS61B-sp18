import synthesizer.GuitarString;

/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHero {

    public static void main(String[] args) {
        // create guitar string for each keyboard
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] strings = new GuitarString[37];
        for (int i = 0; i < 37; i+=1 ) {
            strings[i] = new GuitarString(440 * Math.pow(2, (double) (i - 24) / 12));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyIndex = keyboard.indexOf(key);
                if (keyIndex < 0) continue; // unexpected key
                strings[keyIndex].pluck();
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (GuitarString string : strings) {
                sample += string.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString string : strings) {
                string.tic();
            }
        }
    }
}

