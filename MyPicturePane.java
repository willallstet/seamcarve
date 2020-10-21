package seamcarve;

import java.util.Arrays;

import javafx.scene.layout.BorderPane;
import support.seamcarve.*;


/**
 * This class is your seam carving picture pane.  It is a subclass of PicturePane,
 * an abstract class that takes care of all the drawing, displaying, carving, and
 * updating of seams and images for you.  Your job is to override the abstract
 * method of PicturePane that actually finds the lowest cost seam through
 * the image.
 *
 * See method comments and handouts for specifics on the steps of the seam carving algorithm.
 *
 *
 * @version 01/17/2019
 */

public class MyPicturePane extends PicturePane {



	/**
	 * The constructor accepts an image filename as a String and passes
	 * it to the superclass for displaying and manipulation.
	 *
	 * @param pane
	 * @param filename
	 */
	public MyPicturePane(BorderPane pane, String filename) {
		super(pane, filename);

	}


	/**
	 * In this method, you'll implement the dynamic programming algorithm
	 * that you learned on the first day of class to find the lowest cost seam from the top
	 * of the image to the bottom. BEFORE YOU START make sure you fully understand how the algorithm works
	 * and what it's doing.
	 * See the handout for some helpful resources and use hours/piazza to clarify conceptual blocks
	 * before you attempt to write code.
	 *
	 * This method returns an array of ints that represents a seam.  This size of this array
	 * is the height of the image.  Each entry of the seam array corresponds to one row of the
	 * image.  The data in each entry should be the x coordinate of the seam in this row.
	 * For example, given the below "image" where s is a seam pixel and - is a non-seam pixel
	 *
	 * - s - -
	 * s - - -
	 * - s - -
	 * - - s -
	 *
	 *
	 * the following code will properly return a seam:
	 *
	 * int[] currSeam = new int[4];
	 * currSeam[0] = 1;
	 * currSeam[1] = 0;
	 * currSeam[2] = 1;
	 * currSeam[3] = 2;
	 * return currSeam;
	 *
	 *
	 * This method is protected so it is accessible to the class MyPicturePane and is not
	 * accessible to other classes. PLEASE DO NOT CHANGE THIS!
	 *
	 * @return the lowest cost seam of the current image
 	 */
	public int[][] calcImportance(){//helper function to create importance array
		int[][] importances=new int[getPicHeight()][getPicWidth()];//creates importance array
		for(int i=0;i<getPicWidth();i++) {//goes through each pixel in the photo (w*h)
			for(int j=0; j<getPicHeight();j++) {
				int currentImportance=0;
				javafx.scene.paint.Color currentColor=getPixelColor(j, i);
				if((j==0 && i>0)&& i<getPicWidth()-1) {//for values in the first row, excluding corners
					//directly below
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i))));
					//below and to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i-1))));
					//below and to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i+1))));
					//to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i-1))));
					//to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i+1))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}

				if((j==getPicHeight()-1 && i>0)&& i<getPicWidth()-1) {//for values in the last row, excluding corners
					//directly above
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i))));
					//above and to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i-1))));
					//above and to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i+1))));
					//to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i-1))));
					//to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i+1))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}
				if((i==0 && j>0) && j<getPicHeight()-1) {//for values in the first col, excluding corners
					//directly above
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i))));
					//directly below
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i))));
					//to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i+1))));
					//above and to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i+1))));
					//below and to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i+1))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}
				if((i==getPicWidth()-1 && j>0) && j<getPicHeight()-1) {//for values in the last col, excluding corners
					//directly above
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i))));
					//directly below
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i))));
					//to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i-1))));
					//above and to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i-1))));
					//below and to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i-1))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}
				if((i>0 && j>0)&&(i<getPicWidth()-1 && j<getPicHeight()-1)) {//for values not bordering anything
					//directly above
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i))));
					//directly below
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i))));
					//to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i-1))));
					//above and to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i-1))));
					//below and to the left
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i-1))));
					//to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i+1))));
					//above and to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i+1))));
					//below and to the right
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i+1))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}
				if(i==0 && j==0) {//upper left corner
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i+1))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}
				if(i==0 && j==getPicHeight()-1) {//upper right corner
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i+1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i+1))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}
				if(i==getPicWidth()-1 && j==0) {//lower left corner
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j+1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j+1,i))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}
				if(i==getPicWidth()-1 && j==getPicHeight()-1) {//lower right corner
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i-1))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorRed(currentColor))-Math.abs(getColorRed(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorGreen(currentColor))-Math.abs(getColorGreen(getPixelColor(j-1,i))));
					currentImportance=currentImportance+Math.abs(Math.abs(getColorBlue(currentColor))-Math.abs(getColorBlue(getPixelColor(j-1,i))));
					importances[j][i]=currentImportance;
					currentImportance=0;
				}
			}
		}
		return importances;
	}
	protected int[] findLowestCostSeam() {
		int[][] importanceVals=calcImportance();//get importance array
		int[][] costs=new int[getPicHeight()][getPicWidth()];//create costs array
		int[][] dirs=new int[getPicHeight()][getPicWidth()];//create directions array
		int[] seam=new int[getPicHeight()];//seam array
		for(int i=0; i<getPicWidth();i++) {//fill in the first cost array with the values from the top row
			costs[0][i]=importanceVals[0][i];
			dirs[0][i]=0;//direction doesn't really matter since it won't be used
		}
		
		for(int i=1;i<getPicHeight();i++) {//for each pixel O(w*h)
			for(int j=0;j<getPicWidth();j++) {
				if(j>0 && j<getPicWidth()-1) {//not near a side border
					costs[i][j]=importanceVals[i][j]+Math.min(Math.min(costs[i-1][j-1],costs[i-1][j]),costs[i-1][j+1]);//find the fastest path
					if(costs[i][j]==importanceVals[i][j]+costs[i-1][j]) {//if it was down, dirs=0
						dirs[i][j]=0;
					}
					if(costs[i][j]==importanceVals[i][j]+costs[i-1][j+1]) {//to the left
						dirs[i][j]=1;
					}
					if(costs[i][j]==importanceVals[i][j]+costs[i-1][j-1]) {//to the right
						dirs[i][j]=-1;
						}
				}
				if(j==0) {//on the left side
					costs[i][j]=importanceVals[i][j]+Math.min(costs[i-1][j],costs[i-1][j+1]);
					if(costs[i][j]==importanceVals[i][j]+costs[i-1][j]) {
						dirs[i][j]=0;
					}
					if(costs[i][j]==importanceVals[i][j]+costs[i-1][j+1]) {
						dirs[i][j]=1;
					}
				}
				if(j==getPicWidth()-1) {//on the left side
					costs[i][j]=importanceVals[i][j]+Math.min(costs[i-1][j],costs[i-1][j-1]);
					if(costs[i][j]==importanceVals[i][j]+costs[i-1][j]) {
						dirs[i][j]=0;
					}
					if(costs[i][j]==importanceVals[i][j]+costs[i-1][j-1]) {
						dirs[i][j]=-1;
					}
				}
			}
		}
		int min=costs[getPicHeight()-1][0];//make the smallest value equal to the first value
		int minPos= 0;
		for(int i = 1;i<getPicWidth();i++) {//check each pixel in the top row
			if(costs[getPicHeight()-1][i]<min) {//if it has a smaller cost, it is the new min
				min=costs[getPicHeight()-1][i];
				minPos=i;
			}
		}
		seam[getPicHeight()-1]=minPos;//start at the min
		for(int i = getPicHeight()-2;i>=0;i--) {//create the seam by adding directions
			seam[i]=seam[i+1]+dirs[i+1][seam[i+1]];
		}
		return seam;//return the seam
	}
}
