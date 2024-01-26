import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransposeNotes {

    public static List<List<Integer>> transposeNotes(List<List<Integer>> notes, int semitones) {
        List<List<Integer>> transposedNotes = new ArrayList<>();

        for (List<Integer> note : notes) {
            int newOctave = note.get(0);
            int newNoteNumber = note.get(1) + semitones;

            // Adjust octave and note number if note goes out of bounds
            while (newNoteNumber > 11) {
                newOctave++;
                newNoteNumber -= 12;
            }
            while (newNoteNumber < 0) {
                newOctave--;
                newNoteNumber += 12;
            }

            // Check if the resulting note is within the keyboard range
            if (newOctave < -3 || newOctave > 5) {
                System.err.println("Error: Transposition out of keyboard range");
                return null;
            }

            List<Integer> transposedNote = new ArrayList<>();
            transposedNote.add(newOctave);
            transposedNote.add(newNoteNumber);
            transposedNotes.add(transposedNote);
        }

        return transposedNotes;
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //add your path here
        try (FileReader reader = new FileReader("C:/Users/Sameer Mohsin/Desktop/input.json")) {
            // Read input JSON file
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

            // Convert JSON array to list of lists
            List<List<Integer>> inputNotes = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                List<Integer> note = new ArrayList<>();
                note.add(element.getAsJsonArray().get(0).getAsInt());
                note.add(element.getAsJsonArray().get(1).getAsInt());
                inputNotes.add(note);
            }

            // Transpose notes by -3 semitones
            List<List<Integer>> transposedResult = transposeNotes(inputNotes, -3);

            if (transposedResult != null) {
                // Output the transposed notes to a new JSON file add your path here
                try (FileWriter writer = new FileWriter("C:/Users/Sameer Mohsin/Desktop/output.json")) {
                    gson.toJson(transposedResult, writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
