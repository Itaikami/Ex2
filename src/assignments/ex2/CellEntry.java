package assignments.ex2;
// Add your documentation below:

import java.util.Objects;

/**this class represents each cell entry of the user
 * can be between a-z and 0-99 in the form of <letter><number>
 */
public class CellEntry  implements Index2D {
private String X;
private int Y;

    /** a basic constructor for CellEntry which handles Exceptions
     * @param s is the String that we build the CellEntry from
     */
    public CellEntry(String s)
{
try {
    setX(s.substring(0,1).toUpperCase());
}catch (IndexOutOfBoundsException e)
{setX("");}

        try{
    setY( Integer.parseInt(s.substring(1)));} catch (NumberFormatException |IndexOutOfBoundsException e) {
            setY(-1);

        }

}

    /** A constructor for CellEntry that receives 2 integers and creates a CellEntry
     * @param x the column of the cellentry
     * @param y the row of the cellentry
     */
    public CellEntry(int x,int y)
{
    if(x>=0&&x<Ex2Utils.ABC.length)
    {setX(deterXfromnumber(x));}
    if(y>=0&&y<100)
    {setY(y);}
}
    @Override
    public boolean isValid() {
        boolean  ans=false;
        try {
              ans =(this.getX() >= 0 && this.getX() < 26 && this.getY() >= 0 && this.getY() < 100);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
       return ans;
    }
    public void setX(String s)
    {
       this.X=s;
    }
    public void setY(int i)
    {
        this.Y=i;

    }

    /**Determines the Column of the CellEntry from an integer
     * @param n the number which is cast to a letter
     * @return a letter representing the column
     */
    public static String deterXfromnumber(int n)
    {
        if(n>=0&&n<Ex2Utils.ABC.length)
        {for (int i = 0; i < Ex2Utils.ABC.length; i++) {
            if(n==i)
            {return Ex2Utils.ABC[i];}
        }}
        return "";
    }
    /**determines the integer value of a string
     * if its between a-z or A-Z
     * @param s representing "X"
     * @return the value of X as a number
     */
    public static int deterXfromstring(String s)
    {
        boolean found=false;
        int i=0,j=Ex2Utils.ERR;

        while(!found&&i<26)
        {
            if(s.toUpperCase().equals(Ex2Utils.ABC[i]))
            {
                found=true;
                j=i;
            }
            i++;
        }
        return j;
    }

    @Override
    public int getX() {
        return deterXfromstring(this.X);
    }

    @Override
    public int getY() {
        if(this.Y>=0)
        return this.Y;
        else
        {return Ex2Utils.ERR;}
    }
    @Override
    public String toString()
    {
        return deterXfromnumber(getX())+Integer.toString(getY());
    }

    public boolean equals(CellEntry c) {
        return c.getX()==getX()&&c.getY()==getY();
    }
}
