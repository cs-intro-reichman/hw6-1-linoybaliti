import java.awt.Color;

/**
 * Demonstrates the scaling (resizing) operation featured by Runigram.java. 
 * The program recieves three command-line arguments: a string representing the name
 * of the PPM file of a source image, and two integers that specify the width and the
 * height of the scaled, output image. For example, to scale/resize ironman.ppm to a width
 * of 100 pixels and a height of 900 pixels, use: java Editor2 ironman.ppm 100 900
 */
public class Editor2 {

    public static void main (String[] args){
        // 1. קבלת הארגומנטים מהמשתמש
        // args[0] הוא שם הקובץ (String)
        // args[1] הוא הרוחב המבוקש (הופך מ-String ל-int)
        // args[2] הוא הגובה המבוקש (הופך מ-String ל-int)
        String fileName = args[0];
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);

        // 2. קריאת תמונת המקור מהקובץ
        Color[][] imageIn = Runigram.read(fileName);    
        
        // 3. יצירת התמונה החדשה (המשונה) באמצעות פונקציית ה-scaled שבנית ב-Runigram
        Color[][] imageOut = Runigram.scaled(imageIn, width, height);
        
        // 4. הצגת תמונת המקור בחלון (קנבס) שמתאים לממדים שלה
        Runigram.setCanvas(imageIn);
        Runigram.display(imageIn);
        
        // המתנה של 3 שניות כדי שנוכל לראות את ההבדל
        StdDraw.pause(3000); 
        
        // 5. שינוי גודל החלון (קנבס) שיתאים לממדים של התמונה החדשה
        // זה שלב קריטי כי התמונה החדשה והישנה לא באותו גודל!
        Runigram.setCanvas(imageOut);
        Runigram.display(imageOut);            
    }
}