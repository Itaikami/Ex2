package assignments.ex2;
// Add your documentation below:

import java.util.Objects;

/**this class represents each cell entry of the user
 * can be between a-z and 0-99 in the form of <letter><number>
 */
public class CellEntry  implements Index2D {
private String X;
private int Y;
public CellEntry(String s)
{
        setX(s.substring(0,1));
        try{
    setY( Integer.parseInt(s.substring(1)));} catch (NumberFormatException e) {
            setY(-1);
        }

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
    /**determines the integer value of a string
     * if its between
     * @param s representing "X"
     * @return the value of X as a number
     */
    public static int deterX(String s)
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
        return deterX(this.X);
    }

    @Override
    public int getY() {
        if(this.Y>=0)
        return this.Y;
        else
        {return Ex2Utils.ERR;}
    }
}
