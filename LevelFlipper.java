package CAMELRUSH;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelFlipper {
    public static void main(String[] args) throws Exception{
        String level = "test_level.txt";
        BufferedReader reader = new BufferedReader(new FileReader("CAMELRUSH/levels/"+level));
        String line;
        ArrayList<String[]> list = new ArrayList<>();
        while ((line = reader.readLine())!=null) {
            String[] tokens = line.split("");
            list.add(tokens);
        }
        String filepath = "CAMELRUSH/levels/f"+level;
        File file = new File(filepath);
        FileWriter writer = new FileWriter("CAMELRUSH/levels/f"+level, true);
        try {
            for(int i = 0; i<list.get(0).length; i++){
                for(int j = 0; j<list.size(); j++){
                    if (file.createNewFile()) {
                        writer.write(list.get(list.size()-1-j)[i]);
                    } else {
                        writer.write(list.get(list.size()-1-j)[i]);
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        writer.close();
    }
} 
