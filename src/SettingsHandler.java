import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class SettingsHandler {
    public static String filepath = "CAMELRUSH/local/settings.csv";
    public static void main(String[] args) {
        
        File file = new File(filepath);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            String data = "Volume,100\n";
            FileWriter writer = new FileWriter(filepath);
            writer.write(data);
            writer.close();

            System.out.println("Successfully wrote data to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    public void settingsUpdater(String setting, String value) throws IOException {
        FileReader reader = new FileReader(filepath);
        int character;
        String data = "";
        while ((character = reader.read()) != -1) {
            data += (char) character;
        }
        reader.close();
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(",");
            if (parts[0].equals(setting)) {
                lines[i] = setting + "," + value;
            }
        }
        FileWriter writer = new FileWriter(filepath);
        writer.write(data);
        writer.close();
    }
}