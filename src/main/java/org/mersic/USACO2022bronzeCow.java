package org.mersic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class USACO2022bronzeCow {

    public static void solve(int K, char[] cows) {
        int N = cows.length;
        char[] patches = new char[N];
        Arrays.fill(patches, '.'); //Set patches to all .
        int hCover = -1; //starts with no H patches, will index H+K to count for the K distance a cow may walk
        int gCover = -1; //starts with no G patches, will index G+K to count for the K distance a cow may walk
        for (int i = 0; i < N; i++) {
            //for each cow, if that cow isn't already covered by a patch, place a patch as far to the right as possible.
            //Except, if we are close to the end (less than K distance from the end) then just place the patch "here" (i) or "here" (i) minus 1. Whichever one is available.
            //Two neighboring patches will never be the same patch unless K is 0. When K == 0, we are "never" near the end.
            if (cows[i] == 'G' && gCover < i) { //if this cow is not covered....
                if (i + K >= patches.length) { //if we are near the end of the patches.. (Less than K distance from the end of the patches)
                    if (patches[i] != '.') { //If this spot is taken (it's not a ., so it's an H, can't make it a G or some H cow would become uncovered.)
                        patches[i-1] = 'G'; //Set the previous spot to G (If the current spot is taken, the previous spot will not be taken, so take it. -- Why? The only time the current spot and previous spot would both be taken is if K == 0, then we wouldn't be down this branch anyway because i + K would never be >= patches.length).
                    } else {
                        patches[i] = 'G'; //Otherwise, this spot is not taken, so take it.
                    }
                    gCover = N; //Cover is now all the patches.
                }
                else {
                    patches[i+K] = 'G'; //we are not near the end, so (Greedily) go as far to the right as possible to place a 'G' patch.
                    gCover = i + 2 * K; //Gs are now covered as far as I + K (location of the G patch) + another K (how far a cow may walk).
                }
            } else if (cows[i] == 'H' && hCover < i) { //All H logic is similar G logic.
                if (i + K >= patches.length) {
                    if (patches[i] != '.') {
                        patches[i-1] = 'H';
                    } else {
                        patches[i] = 'H';
                    }
                    hCover = N;
                } else {
                    patches[i+K] = 'H';
                    hCover = i + 2 * K;
                }
            }
        }
        int coverCount = 0;
        for (int i = 0; i < N; i++) {
            if (patches[i] != '.') {
                coverCount++;
            }
        }
        System.out.println(coverCount);
        System.out.println(new String(patches));
    }

    public static void main(String[] args) throws Exception {
        String[] input = Files.readAllLines(Path.of(USACO2022bronzeCow.class.getClassLoader().getResource("sample.cow.input").toURI())).toArray(new String[0]);

        int testCases = Integer.parseInt(input[0]);

        for (int i = 1; i < testCases*2; i += 2) {
            String[] NK = input[i].split(" ");
            int N = Integer.parseInt(NK[0]); //don't need it, just the length of cows array.
            int K = Integer.parseInt(NK[1]);
            solve(K, input[i+1].toCharArray());
        }

    }
}
