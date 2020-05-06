package Flowmaker_v2;

import eu.crydee.syllablecounter.SyllableCounter;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main4Lines {
    public static void main(String[] args) {
        new Main4Lines().run();
    }

    void run() {
        List<String> lines = new LinkedList<>();
        try {
            Files.lines(Paths.get("/home/slot/gloom/oisin/gloomyText1.txt")).forEach(x -> lines.add(x));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String patt = genFlowPattern(lines);
        System.out.println(patt);
        saveToMidiNew(new Pattern(patt));
        System.out.println(patt.split(" ").length);
        System.out.println("Pattern:" + patt
                + "\nSaved to JFugue4.mid");
    }

    private String genFlowPattern(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        sb.append("V0 I[Piano] T[Andante] ");
        for(String x : lines){
            int syl = 0;
            if(!x.isBlank()) syl = calcSylNew(x);
            System.out.println(syl+"\t"+x);
            if (x.isBlank()){
                sb.append("Rs ".repeat(16).repeat(4));
                sb.append("| ");
            }else if(syl <= 16){
                int possible = 16-syl;
                int offset = new Random().nextInt(possible);
                System.out.println(syl+"\t"+possible+"\t"+offset);
                System.out.println(offset+"+"+syl+"+"+(16-syl-offset)+"="+(offset+syl+(16-syl-offset)));
                sb.append("Rs ".repeat(offset));
                sb.append("C#s ".repeat(syl));
                sb.append("Rs ".repeat(16-syl-offset));
                //sb.append("C#s ".repeat(syl));
                //sb.append("Rs ".repeat(16-syl));
                sb.append("| ");
            } else {
                sb.append("C#s ".repeat(16));
                sb.append("| ");
                System.out.println("LINE LONGER THAN 18 SYLLABLES! : \n"+x);
            }
        }

        return sb.toString();
        }


    private int calcSylNew(String x) {
        SyllableCounter sc = new SyllableCounter();
        String[] words = x.split(" ");
        int sumSyl = Arrays.stream(words).mapToInt(sc::count).sum();
        return sumSyl;
    }

    static void saveToMidiNew(Pattern pattern){
        try {
            MidiFileManager
                    .savePatternToMidi(pattern, new File("JFugue4.mid"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
