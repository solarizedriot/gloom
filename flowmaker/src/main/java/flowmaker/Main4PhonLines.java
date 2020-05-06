package flowmaker;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main4PhonLines {
    public static void main(String[] args) {
        new Main4PhonLines().run();
    }

    void run(){
        List<PhonLine> lines_phon = new LinkedList<>();
        try {
            Files.lines(Paths.get("/home/slot/gloom/oisin/gloomyText1.txt")).forEach(x -> lines_phon.add(new PhonLine(x)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String patt = genFlowPattern(lines_phon);
        saveToMidi(new Pattern(patt));
        System.out.println("Pattern:" + patt
                + "\nSaved to JFugue4.mid");
    }

    private String genFlowPattern(List<PhonLine> lines) {
        StringBuilder sb = new StringBuilder();
        sb.append("V0 I[Piano] T[Andante] ");
        boolean lastLineOverlap = false;
        for(PhonLine x : lines){
            int syl = (int) x.syllables;
            System.out.println(syl+"\t"+x);
            if (lastLineOverlap && syl <= 12){
                System.out.println("CALLED!");
                sb.append("C#s ".repeat(syl));
                sb.append("Rs ".repeat(12-syl));
                sb.append("| ");
                lastLineOverlap = false;
            } else if (x.line.isBlank()){
                sb.append("Rs ".repeat(16));
                sb.append("| ");
                lastLineOverlap = false;
            }else if(syl <= 16){
                sb.append("C#s ".repeat(syl));
                sb.append("Rs ".repeat(16-syl));
                sb.append("| ");
                lastLineOverlap = false;
            } else if(syl == 17){
                sb.append("C#s ".repeat(16));
                sb.append("| ");
                sb.append("C#s ");
                sb.append("Rs ".repeat(3));
                lastLineOverlap = true;
                System.out.println("LINE 17 SYLLABLES! : \n"+x);
            } else if(syl == 18){
                sb.append("C#s ".repeat(16));
                sb.append("| ");
                sb.append("C#s ".repeat(2));
                sb.append("Rs ".repeat(2));
                lastLineOverlap = true;
                System.out.println("LINE 18 SYLLABLES! : \n"+x);
            } else {
                sb.append("C#s ".repeat(16));
                sb.append("| ");
                sb.append("C#s ".repeat(syl-16));
                sb.append("Rs ".repeat(32-syl));
                lastLineOverlap = false;
                System.out.println("LINE LONGER THAN 18 SYLLABLES! : \n"+x);
            }
            if(!lastLineOverlap){
                sb.append("| ");
            }


        }

        return sb.toString();
    }

    static void saveToMidi(Pattern pattern){
        try {
            MidiFileManager
                    .savePatternToMidi(pattern, new File("JFugue4.mid"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static class PhonLine{
        String line;
        long syllables;
        public PhonLine(String line){
            this.line = line;
            this.syllables = line.chars().filter(c -> c ==' ').count()+1;
        }

        @Override
        public String toString() {
            return line;
        }
    }

    static class TextLine{

    }
}
