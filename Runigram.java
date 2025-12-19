import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */


	// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		for (int i = 0; i<numRows; i++){
			for(int j = 0;j <numCols; j++){
				// 1. קוראים את 3 הערכים הבאים מהקובץ
				int r = in.readInt();
				int g = in.readInt();
				int b = in.readInt();
				// 2+3. יוצרים אובייקט צבע חדש ושומרים אותו במערך
				image[i][j] = new Color(r, g, b);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
	for (int i = 0; i< image.length; i++){
		for (int j=0; j<image[0].length; j++){
			print(image[i] [j]);
		}
		System.out.println();
	}

	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int numCols = image[0].length;
		int numRows = image.length;
		Color flipped[][] = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
    for (int j = 0; j < numCols; j++) {
        // המערך החדש במקום ה"הפוך" מקבל את הערך מהמערך המקורי
        flipped[i][numCols - 1 - j] = image[i][j];
    }
}
		return flipped;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
	int numRows = image.length;
    int numCols = image[0].length;
    Color[][] flipped = new Color[numRows][numCols];

    for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
            // השורה ה-i במערך החדש מקבלת את השורה ה-"הפוכה" מהמערך המקורי
            // הנוסחה היא: (מספר השורות פחות 1) פחות האינדקס הנוכחי
            flipped[i][j] = image[numRows - 1 - i][j];
        }
    }
    return flipped;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		//// Replace the following statement with your code
		/// 
		int NewR = pixel.getRed();
		int newG = pixel.getGreen();
		int NewB = pixel.getBlue();
		int Newlum = (int)((0.299 * NewR) + (0.587 * newG) + (0.114 * NewB));
		return new Color(Newlum,Newlum,Newlum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int numRows = image.length;
		int numCols = image[0].length;
		Color[][] NewImage = new Color [numRows] [numCols];
		for (int i=0; i<numRows; i++){
			for(int j=0; j<numCols; j++){
				NewImage [i][j] = luminance(image[i][j]);
			}
		}
		return NewImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] ResizedImage = new Color[height][width];
		int OriginalH = image.length;
		int OriginalW = image[0].length;
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				int sourceRow = (int) (i * ((double) OriginalH / height));
          		int sourceCol = (int) (j * ((double) OriginalW / width));
            // 4. העתקת הצבע מהמקור ליעד
            ResizedImage[i][j] = image[sourceRow][sourceCol];
			}
		}
		return ResizedImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		// 1. שליפת ערכי ה-RGB של הצבע הראשון
    int r1 = c1.getRed();
    int g1 = c1.getGreen();
    int b1 = c1.getBlue();

    // 2. שליפת ערכי ה-RGB של הצבע השני
    int r2 = c2.getRed();
    int g2 = c2.getGreen();
    int b2 = c2.getBlue();

    // 3. חישוב הערכים החדשים לפי נוסחת הממוצע המשוקלל (אלפא)
    // אנחנו עושים Casting ל-int בסוף כל חישוב כי ערך צבע חייב להיות שלם
    int red   = (int) (alpha * r1 + (1 - alpha) * r2);
    int green = (int) (alpha * g1 + (1 - alpha) * g2);
    int blue  = (int) (alpha * b1 + (1 - alpha) * b2);

    // 4. יצירת אובייקט צבע חדש עם הערכים שחישבנו והחזרתו
    return new Color(red, green, blue);
	}
	

	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		// 1. הגדרת מידות התמונה (שתיהן באותו גודל, אז אפשר לקחת מאחת מהן)
    int rows = image1.length;
    int cols = image1[0].length;

    // 2. יצירת מערך חדש עבור התמונה המעורבבת
    Color[][] blendedImage = new Color[rows][cols];

    // 3. לולאה שעוברת על כל פיקסל ופיקסל
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            // שליפת הפיקסל המקביל מכל תמונה
            Color c1 = image1[i][j];
            Color c2 = image2[i][j];

            // 4. שימוש בפונקציית ה-blend שכתבנו קודם כדי לערבב את שני הפיקסלים
            blendedImage[i][j] = blend(c1, c2, alpha);
        }
    }

    // 5. החזרת התמונה המעורבבת
    return blendedImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		// 1. התאמת גודל תמונת המטרה למקור
    int height = source.length;
    int width = source[0].length;
    Color[][] scaledTarget = scaled(target, width, height);

    // הכנת הקנבס פעם אחת לפני שמתחילים להציג
    setCanvas(source); 

    // 2. לולאה ליצירת n שלבי הביניים
    for (int i = 0; i <= n; i++) {
        // חישוב אלפא - בצעד i=0 אלפא היא 1, בצעד i=n אלפא היא 0
        double alpha = (double) (n - i) / n;

        // 3. יצירת התמונה המעורבבת לשלב הזה
        Color[][] currentStep = blend(source, scaledTarget, alpha);

        // 4. הצגת התמונה על המסך
        display(currentStep);
        
        // (אופציונלי) השהייה קלה כדי שנוכל לראות את האנימציה
        // StdDraw.pause(30); 
    }
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

