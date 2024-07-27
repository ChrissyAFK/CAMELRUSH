import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class leaderboard {
    public static void main(String[] args) {
        String filepath = "CAMELRUSH/local/leaderboard.csv";
        File file = new File(filepath);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                insertdata("NAME", "TIME");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
    }
    
}
public static void insertdata(String name, String time) throws IOException{
    FileWriter writer = new FileWriter("CAMELRUSH/local/leaderboard.csv", true);
    writer.write(name+", "+time+"\n");
    writer.close();
    System.out.println("Successfully wrote to the file.");
}
}